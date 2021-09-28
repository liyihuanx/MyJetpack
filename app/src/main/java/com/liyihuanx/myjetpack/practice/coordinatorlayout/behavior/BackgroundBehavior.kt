package com.liyihuanx.myjetpack.practice.coordinatorlayout.behavior

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.math.MathUtils
import androidx.palette.graphics.Palette
import com.liyihuanx.module_base.utils.StatusBarUtil
import com.liyihuanx.myjetpack.R

/**
 * @author created by liyihuanx
 * @date 2021/9/26
 * @description: 类的描述
 */
class BackgroundBehavior @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    CoordinatorLayout.Behavior<View>(context, attrs) {

    private var drawable: GradientDrawable

    //topBar内容高度
    private var topBarHeight: Int =
        context.resources.getDimensionPixelOffset(com.liyihuanx.module_base.R.dimen.abc_action_bar_default_height_material)

    //滑动内容初始化TransY
    private var contentTransY: Float = context.resources.getDimension(R.dimen.content_trans_y)

    //下滑时终点值
    private var downEndY: Float = context.resources.getDimension(R.dimen.content_trans_down_end_y)

    //图片往上位移值
    private var faceTransY: Float = context.resources.getDimension(R.dimen.face_trans_y)

    init {
        val obtainStyledAttributes = context.obtainStyledAttributes(
            attrs,
            R.styleable.ContentBehavior
        )
        downEndY =
            obtainStyledAttributes.getDimension(R.styleable.ContentBehavior_contentDownMaxY, 0f)
        contentTransY =
            obtainStyledAttributes.getDimension(R.styleable.ContentBehavior_contentTranslateY, 0f)
        obtainStyledAttributes.recycle()

        //抽取图片资源的亮色或者暗色作为蒙层的背景渐变色
        val palette: Palette =
            Palette.from(BitmapFactory.decodeResource(context.resources, R.drawable.cat))
                .generate()
        val vibrantSwatch = palette.vibrantSwatch
        val mutedSwatch = palette.mutedSwatch
        val colors = IntArray(2)
        if (mutedSwatch != null) {
            colors[0] = mutedSwatch.rgb
            colors[1] = StatusBarUtil.getTranslucentColor(mutedSwatch.rgb, 0.6f)
        } else if (vibrantSwatch != null) {
            colors[0] = vibrantSwatch.rgb
            colors[1] = StatusBarUtil.getTranslucentColor(vibrantSwatch.rgb, 0.6f)
        } else {
            colors[0] = Color.parseColor("#4D000000")
            colors[1] = StatusBarUtil.getTranslucentColor(Color.parseColor("#4D000000"), 0.6f)
        }
        drawable = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors)

    }


    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency.id == R.id.llContent;
    }


    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: View,
        layoutDirection: Int
    ): Boolean {
        //设置蒙层背景
        child.findViewById<View>(R.id.vMask).background = drawable
        return false

    }


    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        //计算Content的上滑百分比、下滑百分比
        val upPro: Float = (contentTransY - MathUtils.clamp(
            dependency.translationY,
            topBarHeight.toFloat(),
            contentTransY
        )) / (contentTransY - topBarHeight)
        val downPro: Float = (downEndY - MathUtils.clamp(
            dependency.translationY,
            contentTransY,
            downEndY
        )) / (downEndY - contentTransY)

        val iamgeview = child.findViewById<ImageView>(R.id.ivBg)
        val maskView = child.findViewById<View>(R.id.vMask)

        if (dependency.translationY >= contentTransY) {
            //根据Content上滑百分比位移图片TransitionY
            iamgeview.translationY = downPro * faceTransY
        } else {
            //根据Content下滑百分比位移图片TransitionY
            iamgeview.translationY = faceTransY + 4 * upPro * faceTransY
        }
        //根据Content上滑百分比设置图片和蒙层的透明度
        iamgeview.alpha = 1 - upPro
        maskView.alpha = upPro
        //因为改变了child的位置，所以返回true
        return true
    }


}