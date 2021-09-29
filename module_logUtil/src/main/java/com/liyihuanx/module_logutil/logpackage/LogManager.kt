package com.liyihuanx.module_logutil.logpackage

import com.liyihuanx.module_logutil.HiLogConfig
import com.liyihuanx.module_logutil.HiLogPrinter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

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
        this.logConfig = logConfig
        this.printers.addAll(printers)
    }


    fun addLogPrinter(logPrinter: LogPrinter) {
        printers.add(logPrinter)
    }

    fun removeLogPrinter(logPrinter: LogPrinter) {
        printers.remove(logPrinter)
    }

}