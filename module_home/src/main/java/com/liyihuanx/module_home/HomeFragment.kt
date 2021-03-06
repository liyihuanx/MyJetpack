package com.liyihuanx.module_home

import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liyihuanx.module_common.RouterPath
import com.liyihuanx.module_base.dialog.BaseDialogFragment
import com.liyihuanx.module_base.dialog.CommonDialogBuild
import com.liyihuanx.module_base.fragment.MainFragment
import com.liyihuanx.module_base.utils.asToast
import com.liyihuanx.module_common.bridge.DebugToolProviderImpl
import com.liyihuanx.module_home.databinding.FragmentHomeBinding
import com.liyihuanx.module_logutil.MLog
import kotlinx.android.synthetic.main.fragment_home.*
import java.lang.NullPointerException


/**
 * @author created by liyihuanx
 * @date 2021/8/30
 * @description: 类的描述
 */
class HomeFragment : MainFragment<FragmentHomeBinding>() {
    private val btnActivityList = arrayListOf(
        "SecondActivity",
        "VLayoutActivity",
        "CollapsingActivity",
        "btnDialog",
        "LogDemoActivity",
        "跨模块调用",
        "Crash"
    )

    private val homeAdapter by lazy {
        HomeAdapter().apply {
            this.setOnItemClickListener { _, _, position ->
                when (btnActivityList[position]) {
                    "SecondActivity" -> {
                        ARouter.getInstance()
                            .build(RouterPath.Main.SecondActivity)
                            .withString("test", "123456")
                            .navigation()
                    }
                    "VLayoutActivity" -> {
                        ARouter.getInstance()
                            .build(RouterPath.Main.VLayoutActivity)
                            .navigation()
                    }
                    "CollapsingActivity" -> {
                        ARouter.getInstance()
                            .build(RouterPath.Main.CollapsingActivity)
                            .navigation()
                    }
                    "btnDialog" -> {
                        showDialog()
                    }
                    "LogDemoActivity" -> {
                        ARouter.getInstance()
                            .build(RouterPath.Main.LogDemoActivity)
                            .navigation()
                    }
                    "跨模块调用" -> {
                        val debugToolProviderImpl =
                            ARouter.getInstance().build(RouterPath.LogUtil.ToolDebugDialog)
                                .navigation() as DebugToolProviderImpl
                        debugToolProviderImpl.getDebugToolDialog()?.show(childFragmentManager, "")
                    }
                    "Crash" ->{
                        throw NullPointerException("我制造的空指针异常")
                    }
                }
            }
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    /**
     * 方法引用，但是参数固定写死了
     * 和setOnClickListener(this)一样
     */
    fun onReferencesClick(v: View) {
        when (v) {

        }
    }


    /**
     * 自定义的点击 用lambda去引用
     * 可以随意加参数
     */
    fun onClick() {

    }


    override fun initViewOrData() {
        mBinding.homeHelper = this
        homeAdapter.setNewInstance(btnActivityList)
        rvHome.adapter = homeAdapter
        rvHome.layoutManager = LinearLayoutManager(context)
        MLog.v("111222")
    }

    override fun observeLiveData() {

    }

    override val getTagName: String
        get() = "HomeFragment"


    private fun showDialog() {
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
            .show(childFragmentManager, "")
    }


}

class HomeAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_home) {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.btnActivity, item)
    }
}