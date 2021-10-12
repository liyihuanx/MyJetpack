package com.liyihuanx.module_debugtool

import com.liyihuanx.module_base.utils.AppContext
import java.io.File

/**
 * @ClassName: CrashManager
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/10/12 21:33
 */
object CrashManager {
    private const val JAVA_CRASH = "java_crash"
    private const val NATIVE_CRASH = "native_crash"


    fun init() {
        CrashHandler.init(getJavaCrashDir().absolutePath)
    }


    private fun getJavaCrashDir(): File {
        val javaCrashFile = File(AppContext.getApplicationContext().cacheDir, JAVA_CRASH)
        if (!javaCrashFile.exists()){
            javaCrashFile.mkdir()
        }
        return javaCrashFile
    }

    fun getCrashList(): Array<File>? {
        return CrashHandler.crashFiles()
    }

}