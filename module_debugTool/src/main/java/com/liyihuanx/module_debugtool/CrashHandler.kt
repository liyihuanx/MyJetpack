package com.liyihuanx.module_debugtool

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.os.Process
import android.os.StatFs
import android.text.format.Formatter
import com.liyihuanx.module_base.utils.ActivityManager
import com.liyihuanx.module_base.utils.AppContext
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*
import java.lang.StringBuilder as StringBuilder

/**
 * @ClassName: CrashHandler
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/10/12 20:34
 */
object CrashHandler {
    var CRASH_DIR = "crash_dir"
    fun init(crash_dir: String) {
        Thread.setDefaultUncaughtExceptionHandler(CaughtExceptionHandler())
        this.CRASH_DIR = crash_dir
    }


    private class CaughtExceptionHandler : Thread.UncaughtExceptionHandler {
        private val defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        override fun uncaughtException(t: Thread, e: Throwable) {
            if (!handleException(e) && defaultExceptionHandler != null) {
                defaultExceptionHandler.uncaughtException(t, e)
            }
            restartApp()
        }

        private fun restartApp() {
            val intent: Intent? =
                context.packageManager?.getLaunchIntentForPackage(context.packageName)
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(intent)

            Process.killProcess(Process.myPid())
            System.exit(10)
        }

        private fun handleException(e: Throwable?): Boolean {
            if (e == null) return false
            val collectDeviceInfo = collectDeviceInfo(e)
            saveCrashInfo2File(collectDeviceInfo)
            return true
        }

        private fun saveCrashInfo2File(collectDeviceInfo: String) {
            // crash文件夹
            val crashDir = File(CRASH_DIR)
            if (!crashDir.exists()) {
                crashDir.mkdir()
            }
            val crashFile = File(crashDir, "${formatter.format(Date())}-crash.txt")
            crashFile.createNewFile()

            FileOutputStream(crashFile).use {
                it.write(collectDeviceInfo.toByteArray())
                it.flush()
            }
        }


        private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
        private val LAUNCH_TIME = formatter.format(Date())
        private val context = AppContext.getApplicationContext()
        private fun collectDeviceInfo(e: Throwable): String {
            val stringBuilder = StringBuilder()
            // 品牌
            stringBuilder.append("brand = ${Build.BRAND} \n")
            // 
            stringBuilder.append("rom = ${Build.MODEL} \n")
            stringBuilder.append("os = ${Build.MODEL} \n")
            stringBuilder.append("sdk = ${Build.VERSION.RELEASE} \n")
            // 启动时间
            stringBuilder.append("launch_time = $LAUNCH_TIME \n")
            // 发生异常的时间
            stringBuilder.append("crash_time = ${formatter.format(Date())} \n")
            // 前后台
            stringBuilder.append("forground = ${ActivityManager.instance.front} \n")
            stringBuilder.append("thread = ${Thread.currentThread().name} \n")
            // cpu
            stringBuilder.append("cou_arch = ${Build.CPU_ABI} \n")

            // app信息
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            stringBuilder.append("version_code = ${packageInfo.versionCode} \n")
            stringBuilder.append("version_name = ${packageInfo.versionName} \n")
            stringBuilder.append("package_name = ${packageInfo.packageName} \n")


            val memoryInfo = android.app.ActivityManager.MemoryInfo()
            val activityManager =
                context.getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
            activityManager.getMemoryInfo(memoryInfo)
            // 可用内存
            stringBuilder.append(
                "avail_memory = ${
                    Formatter.formatFileSize(
                        context,
                        memoryInfo.availMem
                    )
                } \n"
            )
            // 总内存
            stringBuilder.append(
                "total_memory = ${
                    Formatter.formatFileSize(
                        context,
                        memoryInfo.totalMem
                    )
                } \n"
            )

            // 存储空间
            val file = Environment.getExternalStorageDirectory()
            val statFs = StatFs(file.path)
            val availableSize = statFs.availableBlocks * statFs.blockSize
            stringBuilder.append(
                "available_size = ${
                    Formatter.formatFileSize(
                        context,
                        availableSize.toLong()
                    )
                } \n"
            )

            val write = StringWriter()
            val printWriter = PrintWriter(write)
            e.printStackTrace(printWriter) // 把异常信息输出到printWriter输出流中里
            // 获取上一级发生异常的信息
            var cause = e.cause
            while (cause != null) {
                cause.printStackTrace(printWriter)
                cause = cause.cause
            }
            printWriter.close()
            stringBuilder.append(write.toString())
            return stringBuilder.toString()
        }

    }

    fun crashFiles(): Array<File>? {
        return File(CRASH_DIR).listFiles()
    }

}