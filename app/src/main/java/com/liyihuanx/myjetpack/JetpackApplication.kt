package com.liyihuanx.myjetpack

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @author created by liyihuanx
 * @date 2021/8/23
 * @description: 类的描述
 */
class JetPackApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)


    }
}