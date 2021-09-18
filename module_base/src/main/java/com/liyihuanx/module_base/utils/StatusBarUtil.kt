package com.liyihuanx.module_base.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager

/**
 * @ClassName: StatusBarUtil
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/9/16 22:50
 */
object StatusBarUtil {

    /**
     * darkContent ,true:白底黑字,false:黑底白字
     *
     * statusBarColor  状态栏的背景色
     *
     * translucent  沉浸式效果，也就是页面的布局延伸到状态栏之下
     */
    fun setStatusBar(
        activity: Activity,
        darkContent: Boolean,
        statusBarColor: Int = Color.TRANSPARENT,
        translucent: Boolean = false,
        alpha: Int = 0
    ) {


        val window = activity.window
        val decorView = window.decorView
        var visibility = decorView.systemUiVisibility

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //请求系统 绘制状态栏的背景色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            //这俩不能同时出现
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = calculateStatusColor(statusBarColor,alpha)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            visibility = if (darkContent) {
                //白底黑字--浅色主题
                visibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                //黑底白字--深色主题
                // java  visibility &= ~ View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                visibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
        }

        if (translucent) {
            //此时 能够使得页面的布局延伸到状态栏之下，但是状态栏的图标 也看不见了,使得状态栏的图标 恢复可见性
            //View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN -- 能够使得我们的页面布局延伸到状态栏之下，但不会隐藏状态栏。也就相当于状态栏是遮盖在布局之上的
            //View.SYSTEM_UI_FLAG_FULLSCREEN -- 能够使得我们的页面布局延伸到状态栏，但是会隐藏状态栏。
            visibility =
                visibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }

        decorView.systemUiVisibility = visibility
    }


    /**
     * 6.0级以上的沉浸式布局
     * 黑底白字
     * @param activity
     */
    fun setWhiteContentStatusBar(activity: Activity) {
        setStatusBar(activity, false, Color.BLACK, true)
    }

    /**
     * 白底黑字
     */
    fun setDarkContentStatusBar(activity: Activity) {
        setStatusBar(activity, true, Color.TRANSPARENT, true)
    }


    /**
     * 获取状态栏高度
     */
    fun getStatusBarHeight(context: Context): Int {
        // 获得状态栏高度
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }


    /**
     * 计算状态栏颜色
     *
     * @param color color值
     * @param alpha alpha值
     * @return 最终的状态栏颜色
     */
    private fun calculateStatusColor(color: Int, alpha: Int): Int {
        if (alpha == 0) {
            return color
        }
        val a = 1 - alpha / 255f
        var red = color shr 16 and 0xff
        var green = color shr 8 and 0xff
        var blue = color and 0xff
        red = (red * a + 0.5).toInt()
        green = (green * a + 0.5).toInt()
        blue = (blue * a + 0.5).toInt()
        return 0xff shl 24 or (red shl 16) or (green shl 8) or blue
    }


    fun addStatusViewWithColor(context: Context, color: Int = Color.TRANSPARENT): View {
        val statusBarView = View(context)
        statusBarView.setBackgroundColor(color)
        return statusBarView
    }
}