package com.liyihuanx.module_logutil.logpackage

import com.google.gson.GsonBuilder
import com.liyihuanx.module_logutil.HiStackTraceUtil
import java.io.File


/**
 * @author created by liyihuanx
 * @date 2021/9/29
 * @description: 暴露给用户使用的入口
 */
object MLog {
    private val className: String = this.javaClass.name
    private val packageName = className.substring(0, className.lastIndexOf('.') + 1);

    fun v(contents: Any?) {
        myLog(LogType.V, LogManager.logConfig.getGlobalTag(), contents)
    }

    fun vTag(tag: String, contents: Any?) {
        myLog(LogType.V, tag, contents)
    }


    private fun myLog(@LogType.TYPE type: Int, tag: String?, contents: Any?) {
        realLog(LogManager.logConfig, type, tag, contents)
    }


    private fun realLog(
        config: LogConfig,
        @LogType.TYPE type: Int,
        tag: String?,
        contents: Any?
    ) {
        if (!config.enable()) {
            return
        }
        val stringBuilder = StringBuilder()
        // 打印线程信息
        if (config.includeThread()) {
            val threadInfo = LogConfig.THREAD_TRACE_FORMATTER.format(Thread.currentThread())
            stringBuilder.append(threadInfo).append("\n")
        }
        // 打印堆栈信息
        if (config.stackTraceDepth() > 0) {
            val stackTrace = LogConfig.STACK_TRACE_FORMATTER.format(
                HiStackTraceUtil.getCroppedRealStackTrack(
                    Throwable().stackTrace,
                    packageName,
                    config.stackTraceDepth()
                )
            )
            stringBuilder.append(stackTrace).append("\n")
        }
        // replace替换转义字符\
        var body = parseBody(contents, config).toString().replace("\\\"", "\"")
        stringBuilder.append(body)
        val printers = (config.printers() ?: LogManager.printers) as List<LogPrinter>
        printers.takeIf {
            it.isNotEmpty()
        }?.forEach {
            it.print(config, type, tag, stringBuilder.toString())
        }
    }


    private fun parseBody(contents: Any?, config: LogConfig): Any {
        if (contents == null) {
            return "输出内容为空"
        }

        if (config.injectJsonParser() == null) {
            return "JsonParser或输出内容为空"
        }

//        if (isPrimitive(contents)) {
//            return contents
//        }

        return config.injectJsonParser()!!.toJson(contents)
    }


    /**
     * 判断是否是基本数据类型
     */
    private fun isPrimitive(value: Any?) : Boolean{
        try {
            if (value != null){
                val file = value.javaClass.getField("TYPE")
                val clazz = file.get(null) as Class<*>

                if (clazz.isPrimitive)
                    return true
            }

        } catch (e:NoSuchFieldError){
            e.printStackTrace()
        } catch (e: IllegalAccessError){
            e.printStackTrace()
        }

        return false
    }


}