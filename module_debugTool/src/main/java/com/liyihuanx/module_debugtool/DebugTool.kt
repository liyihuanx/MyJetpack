package com.liyihuanx.module_debugtool

import android.app.Activity
import android.content.Intent
import android.os.Process
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.view.children
import androidx.fragment.app.FragmentActivity
import com.liyihuanx.module_base.utils.ActivityManager
import com.liyihuanx.module_base.utils.AppContext
import com.liyihuanx.module_base.utils.SpUtil
import com.liyihuanx.module_logutil.LogManager
import com.liyihuanx.module_logutil.MLog
import com.liyihuanx.module_logutil.ViewPrinter
import com.liyihuanx.module_logutil.ViewPrinterDialog


/**
 * @author created by liyihuanx
 * @date 2021/10/11
 * @description: 类的描述
 */
class DebugTool {

    fun buildVersion(): String {
        return "构建版本: ${BuildConfig.VERSION_NAME}"
    }

    fun buildTime(): String {
        return "构建时间: ${BuildConfig.buildTime}"
    }


    fun buildEnvironment(): String {
        val environment = if (BuildConfig.DEBUG) {
            "测试环境"
        } else {
            "正式环境"
        }
        return "运行环境: $environment"
    }


    @DebugAnnotation("Https降级", "一键降级为Http，方便抓包")
    fun degrade2Http() {
        // 保存一个SP
        SpUtil["Http"].saveData("degrade2Http", true)

        val context = AppContext.get().applicationContext
        // 获取启动页的intent
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)

        // 杀掉当前进程
        Process.killProcess(Process.myPid())
    }

//    @DebugAnnotation("LogTest", "")
//    fun log() {
//        MLog.v("LogTest")
//    }
//
//    @DebugAnnotation("Log-View", "")
//    fun logViewSwitch() {
//        val currentActivity = ActivityManager.instance.currentActivity() as FragmentActivity
//        val viewPrinterDialog = ViewPrinterDialog()
//        LogManager.addLogPrinter(viewPrinterDialog)
//        ViewPrinterDialog().show(currentActivity.supportFragmentManager,"ViewPrinterDialog")
//
//        MLog.v("logViewSwitch")

//        ActivityManager.instance.currentActivity()?.let {
//            ViewPrinter(it)
//        }.takeIf {
//            it != null
//        }?.also {
//            if (LogManager.containsLogPrinter(it)) {
//                LogManager.removeLogPrinter(it)
//            } else {
//                LogManager.addLogPrinter(it)
//            }
//        }
//    }
}