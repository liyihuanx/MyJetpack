package com.liyihuanx.module_common.bridge

import android.content.Context
import androidx.fragment.app.DialogFragment
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * @author created by liyihuanx
 * @date 2021/10/11
 * @description: 类的描述
 */
interface IDebugToolProvider : BaseDialogProvider {
    fun getDebugToolDialog(): DialogFragment?
}