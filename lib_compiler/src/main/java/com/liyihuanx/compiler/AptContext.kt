package com.liyihuanx.compiler

import javax.annotation.processing.Filer
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.Diagnostic

object AptContext {
    lateinit var types: Types
    lateinit var elements: Elements
    lateinit var messager: Messager
    lateinit var filer: Filer

    fun init(env: ProcessingEnvironment){
        elements = env.elementUtils
        types = env.typeUtils
        messager = env.messager
        filer = env.filer
    }


    fun note(msg: Any) {
        messager.printMessage(Diagnostic.Kind.NOTE, ">>>>>>>>>> $msg <<<<<<<<<   ")
    }

    fun error(msg: Any) {
        messager.printMessage(Diagnostic.Kind.ERROR, ">>>>>>>>>> $msg <<<<<<<<<  ")
    }

    fun warn(msg: Any) {
        messager.printMessage(Diagnostic.Kind.WARNING, ">>>>>>>>>> $msg <<<<<<<<<   ")
    }
}