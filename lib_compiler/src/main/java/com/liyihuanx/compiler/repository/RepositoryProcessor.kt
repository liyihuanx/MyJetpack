package com.liyihuanx.compiler.repository

import com.google.auto.service.AutoService
import com.liyihuanx.annotation.AutoApi
import com.liyihuanx.annotation.AutoFlowApi
import com.liyihuanx.annotation.NetStrategy
import com.liyihuanx.compiler.ANNOTATION_NAME
import com.liyihuanx.compiler.AptContext
import com.liyihuanx.compiler.autoApi.AutoMethod
import com.liyihuanx.compiler.autoFlowApi.AutoFlowMethod
import com.liyihuanx.compiler.transformFromKaptPathToAptPath
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement

/**
 * @author created by liyihuanx
 * @date 2021/9/6
 * @description: 类的描述
 */
@AutoService(Processor::class)
@SupportedAnnotationTypes(ANNOTATION_NAME)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class RepositoryProcessor : AbstractProcessor() {

    /**
     * 输出路径
     */
    private lateinit var mOutputDirectory: String

    /**
     * 输出路径key 可通过这个判断被注解的包
     */
    private val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"


    //写成全局的和局部的有什么区别呢，看起来没有太大区别
    private val repositoryMap = HashMap<Element, RepositoryClass>()


    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        AptContext.init(processingEnv)
        AptContext.note("init")

        // 定位
        for (item in processingEnv.options) {
            if (item.key == KAPT_KOTLIN_GENERATED_OPTION_NAME) {
                mOutputDirectory = item.value.transformFromKaptPathToAptPath()
            }
        }

    }


    override fun process(annotations: MutableSet<out TypeElement>, env: RoundEnvironment): Boolean {
        if (annotations.isEmpty()) {
            AptContext.note("没有使用过注解")
            return false
        }
//        val repositoryMap = HashMap<Element, RepositoryClass>()

        env.getElementsAnnotatedWith(AutoFlowApi::class.java).forEach {
            // 拿到使用注解的 xxxService
            //  typeElement 表示类或接口程序元素。 提供对类型及其成员的信息的访问。
            val typeElement = it.enclosingElement as TypeElement
            // 查找
            var repositoryClass = repositoryMap[typeElement]
            if (repositoryClass == null) {
                repositoryClass =
                    RepositoryClass(typeElement)
                repositoryMap[typeElement] = repositoryClass
            }
            // ExecutableElement 表示类或接口的方法，构造函数或初始化程序（静态或实例），包括注释类型元素。
            repositoryClass.methods.add(AutoFlowMethod(it as ExecutableElement))
        }


        env.getElementsAnnotatedWith(AutoApi::class.java).forEach {
            // 拿到使用注解的 com.liyihuanx.myjetpack.ConfigService
            //  typeElement 表示类或接口程序元素。 提供对类型及其成员的信息的访问。
            val typeElement = it.enclosingElement as TypeElement
            // typeElement.simpleName // 类名
            // 查找
            var repositoryClass = repositoryMap[typeElement]
            if (repositoryClass == null) {
                repositoryClass =
                    RepositoryClass(typeElement)
                repositoryMap[typeElement] = repositoryClass!!
            }
            // ExecutableElement 表示类或接口的方法，构造函数或初始化程序（静态或实例），包括注释类型元素。
            repositoryClass!!.methods.add(AutoMethod(it as ExecutableElement))
//            val executableElement = it as ExecutableElement
//            executableElement.simpleName //方法名
        }

        //  如果注解使用在变量上，可以使用获取对应的类 it.enclosingElement.enclosingElement as TypeElement
        //  如果注解使用在变量上，可以使用获取对应的方法 it.enclosingElement as ExecutableElement
        // 因为是用方法名做匹配的，所以必须要放在最后面
        env.getElementsAnnotatedWith(NetStrategy::class.java).forEach {
            val typeElement = it.enclosingElement as TypeElement
            // 查找类
            var repositoryClass = repositoryMap[typeElement]
            if (repositoryClass == null) {
                repositoryClass = RepositoryClass(typeElement)
                repositoryMap[typeElement] = repositoryClass!!
            }


            val executableElement = it as ExecutableElement
            // 找方法 使用了这个注解的方法名字
            val userCacheStrategyMethod = executableElement.simpleName.toString()
            // 再找到类的方法集合，遍历一下
            repositoryClass!!.methods.forEach { method ->
                if (method.methodName == userCacheStrategyMethod) {
                    val annotation = executableElement.getAnnotation(NetStrategy::class.java)
                    // 找到类后，获取注解的值,并且做保存
                    method.netStrategy = annotation.strategy
                    method.isUserStrategyParameter = annotation.isNeedAddParameter

                    method.isUserStrategyFunction = !annotation.isNeedAddParameter
                }
            }
        }

        // 生成
        repositoryMap.forEach { (k, repositoryClass) ->
            RepositoryClassBuilder(repositoryClass)
                .build(AptContext.filer, mOutputDirectory)
        }

        return true
    }
}