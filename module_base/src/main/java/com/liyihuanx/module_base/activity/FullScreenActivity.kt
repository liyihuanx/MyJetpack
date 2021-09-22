package com.liyihuanx.module_base.activity

import android.graphics.Color
import androidx.databinding.ViewDataBinding
import com.liyihuanx.module_base.utils.StatusBarUtil

/**
 * @author created by liyihuanx
 * @date 2021/9/22
 * @description: 延伸到顶部状态栏
 */
abstract class FullScreenActivity<T : ViewDataBinding> : BaseActivity<T>() {

    override fun setViewStatusBar() {
        StatusBarUtil.setStatusBar(this, true, Color.TRANSPARENT, true)
    }

}