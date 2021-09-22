package com.liyihuanx.module_base.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.get
import androidx.databinding.ViewDataBinding
import com.liyihuanx.module_base.R
import com.liyihuanx.module_base.utils.StatusBarUtil

/**
 * @author created by liyihuanx
 * @date 2021/9/18
 * @description: 类的描述
 */
abstract class MainFragment<T : ViewDataBinding> : BaseLazyFragment<T>() {

    override fun isNeedViewStatus(): Boolean {
        return true
    }

}