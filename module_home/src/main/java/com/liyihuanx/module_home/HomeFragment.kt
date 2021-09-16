package com.liyihuanx.module_home

import android.util.Log
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.liyihuan.module_common.RouterPath
import com.liyihuan.module_common.utils.LiveDataBus
import com.liyihuanx.module_base.fragment.BaseFragment
import com.liyihuanx.module_base.fragment.BaseLazyFragment
import com.liyihuanx.module_home.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @author created by liyihuanx
 * @date 2021/8/30
 * @description: 类的描述
 */
class HomeFragment : BaseLazyFragment<FragmentHomeBinding>(){
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }


    /**
     * 方法引用，但是参数固定写死了
     * 和setOnClickListener(this)一样
     */
    fun onReferencesClick(v: View) {
        when (v) {
            btnToSecond -> {
                ARouter.getInstance()
                    .build(RouterPath.Main.secondActivity)
                    .withString("test", "123456")
                    .navigation()
            }

            btnSendLiveData -> {
                LiveDataBus.with<String>("TestLiveDataBus").postStickyData("测试！")
            }
        }
    }

    /**
     * 自定义的点击 用lambda去引用
     * 可以随意加参数
     */
    fun onClick(){

    }


    override fun initViewOrData() {
        mBinding.homeHelper = this
    }

    override fun observeLiveData() {

    }

    override val getTagName: String
        get() = "HomeFragment"
}