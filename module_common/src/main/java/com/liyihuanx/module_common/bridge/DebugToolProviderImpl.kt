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
    override fun getDebugToolDialog(): DialogFragment {
        return DebugToolDialog()
    }
}