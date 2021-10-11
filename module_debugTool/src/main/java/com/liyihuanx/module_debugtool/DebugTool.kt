package com.liyihuanx.module_debugtool

import com.liyihuanx.module_logutil.BuildConfig


/**
 * @author created by liyihuanx
 * @date 2021/10/11
 * @description: 类的描述
 */
class DebugTool {

    fun buildVersion(): String {
        return "构建版本: ${BuildConfig.VERSION_NAME}"
    }

    fun buildTime():String {
        return "构建时间: ${BuildConfig.buildTime}"
    }


    fun buildEnvironment():String {
        return "运行环境: ${BuildConfig.DEBUG}"
    }


    @DebugAnnotation("Https降级","一键降级为Http，方便抓包")
    fun degrade2Http(){

    }

}