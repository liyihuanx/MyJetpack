package com.liyihuanx.myjetpack

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.liyihuanx.module_base.utils.ActivityManager
import com.liyihuanx.module_logutil.HiConsolePrinter
import com.liyihuanx.module_logutil.HiFilePrinter
import com.liyihuanx.module_logutil.HiLogConfig
import com.liyihuanx.module_logutil.HiLogManager
import com.liyihuanx.module_logutil.logpackage.ConsolePrinter
import com.liyihuanx.module_logutil.logpackage.LogConfig
import com.liyihuanx.module_logutil.logpackage.LogManager

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

        LogManager.init(
            arrayListOf(
                ConsolePrinter()
            )
        )
        HiLogManager.init(
            object : HiLogConfig() {
                override fun injectJsonParser(): JsonParser? {
                    return JsonParser { src -> Gson().toJson(src) }
                }

                override fun getGlobalTag(): String {
                    return "MApplication"
                }

                override fun enable(): Boolean {
                    return true
                }

                override fun includeThread(): Boolean {
                    return true
                }

                override fun stackTraceDepth(): Int {
                    return 5
                }
            },
            HiConsolePrinter(),
            HiFilePrinter.getInstance(applicationContext.cacheDir.absolutePath, 0)
        )

    }
}