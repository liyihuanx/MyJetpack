package com.liyihuanx.myjetpack.practice

import com.alibaba.android.arouter.facade.annotation.Route
import com.liyihuanx.module_base.activity.BaseActivity
import com.liyihuanx.module_common.RouterPath
import com.liyihuanx.module_logutil.LogManager
import com.liyihuanx.module_logutil.MLog
import com.liyihuanx.module_logutil.ViewPrinter
import com.liyihuanx.myjetpack.R
import kotlinx.android.synthetic.main.activity_log_demo.*

/**
 * @author created by liyihuanx
 * @date 2021/9/28
 * @description: 类的描述
 */
@Route(path = RouterPath.Main.LogDemoActivity)
class LogDemoActivity : BaseActivity() {

    override fun getLayout(): Int {
        return R.layout.activity_log_demo
    }

    override fun bindViewOrData() {
        btnLog.setOnClickListener {
            MLog.e("6789")
        }
        val testViewPrinter = ViewPrinter(this)
        LogManager.addLogPrinter(testViewPrinter)
    }
}