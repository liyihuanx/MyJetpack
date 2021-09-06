package com.liyihuanx.compiler.repository

import com.google.auto.service.AutoService
import com.liyihuanx.annotation.AutoApi
import com.liyihuanx.compiler.ANNOTATION_NAME
import com.liyihuanx.compiler.AptContext
import com.liyihuanx.compiler.transformFromKaptPathToAptPath
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

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
    private  val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"


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
        AptContext.note(mOutputDirectory)

    }


    override fun process(annotations: MutableSet<out TypeElement>, env: RoundEnvironment): Boolean {
        env.getElementsAnnotatedWith(AutoApi::class.java).forEach {
            // 拿到使用注解的 MainActivity
            //  typeElement 表示类或接口程序元素。 提供对类型及其成员的信息的访问。
            val typeElement = it.enclosingElement as TypeElement
            // 查找
            var repositoryClass = repositoryMap[typeElement]
            if (repositoryClass == null) {
                repositoryClass = RepositoryClass(typeElement)
                repositoryMap[typeElement] = repositoryClass
            }
            // ExecutableElement 表示类或接口的方法，构造函数或初始化程序（静态或实例），包括注释类型元素。
            repositoryClass.methods.add(AutoMethod(it as ExecutableElement))
        }


        // 生成
//        repositoryMap.forEach { k, repositoryClass ->
//            RepositoryClassBuilder(repositoryClass).build(AptContext.filer, mOutputDirectory)
//        }

        return true
    }
}