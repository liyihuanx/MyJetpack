package com.liyihuanx.module_common.bridge

import androidx.fragment.app.DialogFragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.liyihuanx.module_common.RouterPath
import com.liyihuanx.module_debugtool.DebugToolDialog

/**
 * @author created by liyihuanx
 * @date 2021/10/11
 * @description: 类的描述
 */
@Route(path = RouterPath.LogUtil.ToolDebugDialog)
class DebugToolProviderImpl : IDebugToolProvider {
    override fun getDebugToolDialog(): DialogFragment? {
        //ARouter
        return DebugToolDialog()

//        var debugToolDialog: DialogFragment? = null
//        try {
//            val clazz = Class.forName("com.liyihuanx.module_debugtool.DebugToolDialog")
//            debugToolDialog = clazz.getConstructor().newInstance() as DialogFragment
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return debugToolDialog
    }
}