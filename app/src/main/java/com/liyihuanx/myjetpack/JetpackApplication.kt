package com.liyihuanx.myjetpack

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.liyihuanx.module_base.utils.ActivityManager

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
    }
}