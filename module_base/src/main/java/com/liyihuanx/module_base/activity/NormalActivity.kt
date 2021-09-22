package com.liyihuanx.module_base.activity

import android.graphics.Color
import androidx.databinding.ViewDataBinding
import com.liyihuanx.module_base.utils.StatusBarUtil

/**
 * @author created by liyihuanx
 * @date 2021/9/22
 * @description: 不会延伸到顶部状态栏
 */
abstract class NormalActivity<T : ViewDataBinding> : BaseActivity<T>() {

    override fun setViewStatusBar() {
        StatusBarUtil.setStatusBar(this, true, Color.WHITE, false)
    }

}