package com.liyihuanx.module_logutil

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.BlockingQueue
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

/**
 * @ClassName: KFilePrinter
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/10/12 23:28
 */
class KFilePrinter(
    private var logPath: String? = null,
    private var retentionTime: Long = 60 * 1000 * 1000L
) : LogPrinter {
    private var writer: LogWriter = LogWriter()

    @Volatile
    private var worker: PrintWorker = PrintWorker()

    init {
        writer = LogWriter()
        worker = PrintWorker()
        cleanExpiredLog()
    }


    companion object {
        private val EXECUTOR = Executors.newSingleThreadExecutor()
//
//        fun getInstance(logPath: String){
//
//        }

        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            KFilePrinter()
        }
    }

    override fun print(config: LogConfig, level: Int, tag: String?, printString: String) {

    }


    inner class PrintWorker : Runnable {
        private val logs: BlockingQueue<LogBean> = LinkedBlockingQueue()

        @Volatile
        private var running = false

        override fun run() {
            var log: LogBean?
            try {
                while (true) {
                    log = logs.take()
                    doPrint(log)
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
                synchronized(this) { running = false }
            }
        }

        /**
         * 将log放入打印队列
         *
         * @param log 要被打印的log
         */
        fun put(log: LogBean?) {
            try {
                logs.put(log)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        /**
         * 判断工作线程是否还在运行中
         *
         * @return true 在运行
         */
        fun isRunning(): Boolean {
            synchronized(this) { return running }
        }

        /**
         * 启动工作线程
         */
        fun start() {
            synchronized(this) {
                EXECUTOR.execute(this)
                running = true
            }
        }


    }

    private fun doPrint(logMo: LogBean) {
        val lastFileName = writer.getPreFileName()
        if (lastFileName == null) {
            val newFileName: String = genFileName()
            if (writer.isReady()) {
                writer.close()
            }
            if (!writer.ready(newFileName)) {
                return
            }
        }

        writer.append(logMo.flattenedLog())
    }

    private fun genFileName(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(Date(System.currentTimeMillis()))
    }

    /**
     * 清除过期log
     */
    private fun cleanExpiredLog() {
        val currentTimeMillis = System.currentTimeMillis()
        val logDir = File(logPath)
        val files = logDir.listFiles() ?: return
        for (file in files) {
            if (currentTimeMillis - file.lastModified() > retentionTime) {
                file.delete()
            }
        }
    }

    inner class LogWriter {

        private var preFileName: String? = null
        private var logFile: File? = null
        private var bufferedWriter: BufferedWriter? = null

        fun isReady(): Boolean {
            return bufferedWriter != null
        }

        fun getPreFileName(): String? {
            return preFileName
        }

        /**
         * log写入前的准备操作
         *
         * @param newFileName 要保存log的文件名
         * @return true 表示准备就绪
         */
        fun ready(newFileName: String): Boolean {
            preFileName = newFileName
            logFile = File(logPath, newFileName)
            // 当log文件不存在时创建log文件
            if (!logFile!!.exists()) {
                try {
                    val parent = logFile!!.parentFile
                    if (!parent.exists()) {
                        parent.mkdirs()
                    }
                    logFile!!.createNewFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                    preFileName = null
                    logFile = null
                    return false
                }
            }
            try {
                bufferedWriter = BufferedWriter(FileWriter(logFile, true))
            } catch (e: Exception) {
                e.printStackTrace()
                preFileName = null
                logFile = null
                return false
            }
            return true
        }

        /**
         * 关闭bufferedWriter
         */
        fun close(): Boolean {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter!!.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                    return false
                } finally {
                    bufferedWriter = null
                    preFileName = null
                    logFile = null
                }
            }
            return true
        }


        /**
         * 将log写入文件
         *
         * @param flattenedLog 格式化后的log
         */
        fun append(flattenedLog: String?) {
            try {
                bufferedWriter!!.write(flattenedLog)
                bufferedWriter!!.newLine()
                bufferedWriter!!.flush()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}