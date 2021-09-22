package com.liyihuanx.module_base.fragment

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.liyihuanx.module_base.R
import com.liyihuanx.module_base.utils.StatusBarUtil

/**
 * @author created by liyihuanx
 * @date 2021/8/23
 * @description: 类的描述
 */
abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    // 布局是否初始化
    var isViewCreated = false

    lateinit var mBinding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        isViewCreated = true
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewOrData()
        observeLiveData()

        if (isNeedViewStatus()){
            addViewStatus()
        }
    }


    abstract fun getLayoutId(): Int
    abstract fun initViewOrData()
    abstract fun observeLiveData()


    /**
     * 是否需要添加一个空白View填充状态栏
     */
    open fun isNeedViewStatus():Boolean{
        return false
    }

    /**
     * 添加状态栏占位视图
     */
    open val viewStatus by lazy {
        StatusBarUtil.addStatusViewWithColor(requireContext()).also {
            it.id = R.id.viewStatus
        }
    }

    private fun addViewStatus() {
        // 添加和状态栏高度一样的view到布局的顶部
        (mBinding.root as ViewGroup).addView(
            viewStatus,
            0,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                StatusBarUtil.getStatusBarHeight(requireContext())
            )
        )

        /**
         *  这里的firstView, 是指在没添加上面的viewStatus时的第一个View
         *  再根据不同布局，添加不同的约束
         */
        when (mBinding.root) {
            is RelativeLayout -> {
                val firstView = (mBinding.root as RelativeLayout)[1]
                val layoutParams = firstView.layoutParams as RelativeLayout.LayoutParams
                layoutParams.addRule(RelativeLayout.BELOW, viewStatus.id)
            }
            /**
             *  ConstraintLayout 原本的第一个View如果想设置为全屏，layout_height不能设置match_parent，不然就算约束了还是会撑满全屏的
             *  因为一般在ConstrainLayout中添加满屏的都是这样的
             *  android:layout_height="0dp"
             *  app:layout_constraintBottom_toBottomOf="parent"
             *  app:layout_constraintTop_toTopOf="parent
             *  且记得要给第一个View加id！！
             */
            is ConstraintLayout -> {
                val constraintLayout = mBinding.root as ConstraintLayout
                val firstView = constraintLayout[1]
                val set = ConstraintSet()
                set.clone(constraintLayout)
                set.connect(firstView.id, ConstraintSet.TOP, viewStatus.id, ConstraintSet.BOTTOM, 0)
                set.applyTo(constraintLayout)
            }

            is FrameLayout -> {
                val firstView = (mBinding.root as FrameLayout)[1]
                val layoutParams = firstView.layoutParams as FrameLayout.LayoutParams
                layoutParams.topMargin = StatusBarUtil.getStatusBarHeight(requireContext())
            }

        }

    }
}