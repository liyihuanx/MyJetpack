package com.liyihuanx.myjetpack.practice

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.liyihuanx.module_base.activity.BaseActivity
import com.liyihuanx.module_common.RouterPath
import com.liyihuanx.module_logutil.*
import com.liyihuanx.myjetpack.R

/**
 * @author created by liyihuanx
 * @date 2021/9/28
 * @description: 类的描述
 */
@Route(path = RouterPath.Main.LogDemoActivity)
class LogDemoActivity : BaseActivity() {
    var viewPrinter: HiViewPrinter? = null

    override fun getLayout(): Int {
        return R.layout.activity_log_demo
    }

    override fun bindViewOrData() {
        viewPrinter = HiViewPrinter(this)
        findViewById<View>(R.id.btn_log).setOnClickListener {
            printLog()
        }
        viewPrinter!!.viewProvider.showFloatingView()
    }

    private fun printLog() {
        HiLogManager.getInstance().addPrinter(viewPrinter)
        HiLog.log(object : HiLogConfig() {
            override fun includeThread(): Boolean {
                return false
            }
        }, HiLogType.V, "---", "5566")

        HiLog.v("9900")
    }
}