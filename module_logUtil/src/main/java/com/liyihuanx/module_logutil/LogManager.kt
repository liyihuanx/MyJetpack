package com.liyihuanx.module_logutil

import kotlin.collections.ArrayList

/**
 * @author created by liyihuanx
 * @date 2021/9/29
 * @description: 类的描述
 */
object LogManager {

    var logConfig: LogConfig = LogConfig.defaultLogConfig
    var printers = ArrayList<LogPrinter>()

    /**
     * 初始化
     */
    fun init(printers: List<LogPrinter>, logConfig: LogConfig = LogConfig.defaultLogConfig) {
        LogManager.logConfig = logConfig
        LogManager.printers.addAll(printers)
    }


    fun addLogPrinter(logPrinter: LogPrinter) {
        if (!printers.contains(logPrinter)) {
            printers.add(logPrinter)
        }
    }

    fun removeLogPrinter(logPrinter: LogPrinter) {
        printers.remove(logPrinter)
    }

    fun containsLogPrinter(logPrinter: LogPrinter): Boolean {
        return printers.contains(logPrinter)
    }
}