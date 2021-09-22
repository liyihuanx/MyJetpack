package com.liyihuanx.module_ui

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * @author created by liyihuanx
 * @date 2021/9/22
 * @description: 类的描述
 */
class CustomViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : ViewPager(context, attrs) {


    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return super.onTouchEvent(ev)
    }

}