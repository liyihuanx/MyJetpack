package com.liyihuanx.module_base.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * @author created by liyihuanx
 * @date 2021/9/16
 * @description: 这个适合Viewpager,和用show，hide方式使用的懒加载
 */
abstract class BaseLazyFragment<T : ViewDataBinding> : BaseFragment<T>() {

    /**
     * 1.第一次加载，不加载当前界面的数据
     * 2.跳转到Activity之后回来不会刷新数据，且缓存的fragment都会走onResume
     * 3.fragment的嵌套加载问题
     */

    // 记录Fragment的可见状态  可见 true  不可见 false
    private var currentVisibleStatus = false

    // 是否第一次加载
    private var isFirstLoad = true

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        rootview = inflater.inflate(getLayoutId(), null)
//
////        initView(rootview!!)
//        isViewCreated = true
//
//        return rootview
//
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // isHidden 如果该Fragment对象已经被隐藏，那么它返回true
        if (!isHidden && userVisibleHint) {
            dispatchVisible(true)
        }
    }


    /**
     *   1.可见--> 不可见
     *   2.不可见--> 可见
     *   isVisibleToUser true 可见，false 不可见
     *   currentVisibleStatus 判断当前的状态, dispatchVisible分发数据才会赋值
     *
     *   1.第一次点击到此Fragment时，
     *      isVisibleToUser == true, 但是没分发过数据，所以currentVisibleStatus == false
     *      符合 不可见到可见 状态 --> dispatchVisible(true) 所以currentVisibleStatus 赋值为 true
     *   2.从此Fragment离开时
     *      isVisibleToUser == false,currentVisibleStatus == true
     *      符合 可见到不可见 状态 --> dispatchVisible(false) 所以currentVisibleStatus 赋值为 false
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.d("QWER", "setUserVisibleHint: $getTagName - $isVisibleToUser")

        // 在界面初始化好后才开始分发可见状态加载数据
        // 但是函数是在生命周期之前调用的，所以第一个显示的fragment界面还没绘制之前，这个函数就已经结束了，所以分发不到事件
        if (isViewCreated) {
            if (!isVisibleToUser && currentVisibleStatus) {
                // 1.可见--> 不可见
                dispatchVisible(false)
            } else if (isVisibleToUser && !currentVisibleStatus) {
                // 2.不可见--> 可见
                dispatchVisible(true)
            }
        }

    }

    /**
     * 分发可见事件，加载数据
     */
    private fun dispatchVisible(isVisible: Boolean) {
        currentVisibleStatus = isVisible

        // 解决viewpager嵌套 子fragment会加载数据的问题
        if (isVisible && isParentVisible()) {
            return
        }

        if (isVisible) {
            if (isFirstLoad) {
                onFragmentFirstVisible()
                isFirstLoad = false
            }
            onFragmentResume()
            dispatchChild(true)
        } else {
            onFragmentPause()
            dispatchChild(false)
        }

    }

    /**
     *
     * 1.当前父Fragment是不可见的，所以子Fragment也不能加载数据
     *      父currentVisibleStatus == false --> 但是isSupportVisible == true 所以isParentVisible--> true --> 直接return了
     * 2.当前父Fragment是可见的，子Fragment也从不可见到可见
     *      父currentVisibleStatus == true --> 但是isSupportVisible == false 所以isParentVisible--> false -->可以分发数据
     *
     */
    private fun isParentVisible(): Boolean {
        if (parentFragment is BaseLazyFragment<*>) {
            return (parentFragment as BaseLazyFragment<*>).isSupportVisible()
        }
        return false
    }


    fun isSupportVisible(): Boolean {
        return !currentVisibleStatus
    }

    override fun onResume() {
        super.onResume()
        Log.d("QWER", "onResume: $getTagName")

        // 跳转Activity回来后，缓存的Fragment会走onResume
        // 如果不是第一次加载就再次加载数据
        if (!isFirstLoad) {
            // isHidden = false, userVisibleHint = true, currentVisibleStatus
            // userVisibleHint && !currentVisibleStatus 不可见--> 可见
            if (!isHidden && userVisibleHint && !currentVisibleStatus && isNeedLoadDataOnResume()) {
                dispatchVisible(true)
            }
        }

    }

    override fun onStop() {
        super.onStop()

        // 表示已经跳转到其他Activity去了
        if (currentVisibleStatus) {
            currentVisibleStatus = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("QWER", "onDestroy: $getTagName")

        isViewCreated = false
    }

    private fun dispatchChild(visible: Boolean) {
        val fragments = childFragmentManager.fragments //  List<Fragment>
        fragments.forEach {
            if (it is BaseLazyFragment<*> && !it.isHidden && it.userVisibleHint) {
                it.dispatchVisible(visible)
            }
        }
    }


    /**
     * 加载数据
     * 停止加载数据
     * 第一次加载数据
     */
    open fun onFragmentResume() {
        Log.d("QWER", "onFragmentResume: $getTagName")
    }
    open fun onFragmentPause() {
        Log.d("QWER", "onFragmentPause: $getTagName")

    }

    /**
     * Fragment第一次可见时
     */
    open fun onFragmentFirstVisible() {}

    /**
     * 从Activity回来需不需要再加载一遍数据
     */
    open fun isNeedLoadDataOnResume(): Boolean {
        return false
    }

    abstract val getTagName: String

}