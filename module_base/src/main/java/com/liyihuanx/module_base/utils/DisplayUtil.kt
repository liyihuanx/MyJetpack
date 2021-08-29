package com.liyihuanx.module_base.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.util.TypedValue
import android.view.WindowManager

/**
 * @ClassName: DisplayUtil
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/8/26 23:49
 */
object DisplayUtil {

    @JvmStatic
    fun dp2px(dp: Float, resources: Resources): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            resources.displayMetrics
        ).toInt()
    }

    @JvmStatic
    fun dp2px(dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            AppContext.get().resources.displayMetrics
        ).toInt()
    }


    @JvmStatic
    fun getDisplayWidthInPx(context: Context): Int {
        val wm =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        if (wm != null) {
            val display = wm.defaultDisplay
            val size = Point()
            display.getSize(size)
            return size.x
        }
        return 0
    }


    @JvmStatic
    fun getDisplayHeightInPx(context: Context): Int {
        val wm =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        if (wm != null) {
            val display = wm.defaultDisplay
            val size = Point()
            display.getSize(size)
            return size.y
        }
        return 0
    }


}