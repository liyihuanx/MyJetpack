package com.liyihuanx.compiler.autoApi

import com.liyihuanx.annotation.AutoApi
import com.liyihuanx.compiler.repository.RepositoryMethod
import com.liyihuanx.compiler.types.asKotlinTypeName
import javax.lang.model.element.ExecutableElement

/**
 * @author created by liyihuanx
 * @date 2021/9/6
 * @description: 类的描述
 */
class AutoMethod(private val mExecutableElement: ExecutableElement) :
    RepositoryMethod(mExecutableElement) {

    override fun initParameters() {
        val defaultVMap = HashMap<String, String>()
        // 拿到注解的中，key-values 他是一 一对应的
        val annotation: AutoApi = executableElement.getAnnotation(AutoApi::class.java)
        annotation.keys.forEachIndexed { index, s ->
            defaultVMap[s] = annotation.defaultValues[index]
        }

        // 收集方法的参数
        executableElement.parameters.forEach {
            parameters.add(
                Parameter(
                    it.simpleName.toString(),
                    it.asType().asKotlinTypeName(),
                    defaultVMap[it.simpleName.toString()]
                )
            )
        }

    }

}