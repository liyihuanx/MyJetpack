package com.liyihuanx.module_home

import android.util.Log
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.launcher.ARouter
import com.liyihuan.module_common.RouterPath
import com.liyihuanx.module_base.dialog.BaseDialogFragment
import com.liyihuanx.module_base.dialog.CommonDialogBuild
import com.liyihuanx.module_base.utils.LiveDataBus
import com.liyihuanx.module_base.fragment.BaseLazyFragment
import com.liyihuanx.module_base.utils.asToast
import com.liyihuanx.module_home.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

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

            btnDialog -> {
                CommonDialogBuild()
                    .setTittle("CommonDialog")
                    .setPositiveText("OK")
                    .setNegativeText("Cancel")
                    .setContent("这是一个通用的按钮")
                    .isNeedCancelBtn(true)
                    .setListener(object : BaseDialogFragment.BaseDialogListener() {
                        override fun onDialogNegativeClick(dialog: DialogFragment, any: Any) {
                            "onDialogNegativeClick".asToast()
                        }

                        override fun onDialogPositiveClick(dialog: DialogFragment, any: Any) {
                            "onDialogPositiveClick".asToast()
                        }

                        override fun onDismiss(dialog: DialogFragment, any: Any) {
                            super.onDismiss(dialog, any)
                        }
                    })
                    .build()
                    .show(childFragmentManager,"")
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