package com.liyihuanx.myjetpack

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.liyihuanx.module_base.utils.ActivityManager
import com.liyihuanx.module_debugtool.CrashHandler
import com.liyihuanx.module_debugtool.CrashManager
import com.liyihuanx.module_logutil.FilePrinter
import com.liyihuanx.module_logutil.ConsolePrinter
import com.liyihuanx.module_logutil.LogManager
import com.liyihuanx.module_logutil.ViewPrinter

/**
 * @author created by liyihuanx
 * @date 2021/8/23
 * @description: 类的描述
 */
class JetPackApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        // 路由
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
        // activity管理器
        ActivityManager.instance.initActivityManager(this)
        // log输出器
        LogManager.init(
            arrayListOf(
                ConsolePrinter(),
                FilePrinter.getInstance(applicationContext.cacheDir.absolutePath, 0)
            )
        )
        CrashManager.init()

    }
}