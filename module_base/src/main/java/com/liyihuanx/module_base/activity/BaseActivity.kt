package com.liyihuanx.module_base.activity

import android.graphics.Color
import com.liyihuanx.module_base.utils.StatusBarUtil

/**
 * @author created by liyihuanx
 * @date 2021/9/26
 * @description: 类的描述
 */
abstract class BaseActivity : BaseFinalActivity() {

    override fun setViewStatusBar() {
        StatusBarUtil.setStatusBar(this, true, Color.WHITE, false)
    }

    override fun initViewForBase() {
        setViewStatusBar()
        bindViewOrData()
        observeLiveData()
    }

}