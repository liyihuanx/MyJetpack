package com.liyihuanx.myjetpack.practice.coordinatorlayout.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.math.MathUtils
import com.liyihuanx.myjetpack.R

/**
 * @author created by liyihuanx
 * @date 2021/9/26
 * @description: 类的描述
 */
class TopBarBehavior @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    CoordinatorLayout.Behavior<View>(context, attrs) {

    private var contentTransY: Float = context.resources.getDimension(R.dimen.content_trans_y)
    private var topBarHeight: Int = context.resources.getDimensionPixelOffset(com.liyihuanx.module_base.R.dimen.abc_action_bar_default_height_material)

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {

        //依赖content
        return dependency.id == R.id.llContent
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        // 计算Content上滑的百分比，设置子view的透明度
        // clamp 判断是否在两个值之间，超过则取边界
        val upPro: Float = (contentTransY - MathUtils.clamp(
            dependency.translationY,
            topBarHeight.toFloat(),
            contentTransY
        )) / (contentTransY - topBarHeight)
        val tvName = child.findViewById<View>(R.id.tvTopBarTitle)
        val tvColl = child.findViewById<View>(R.id.tvTopBarColl)
        tvName.alpha = upPro
        tvColl.alpha = upPro
        return true
    }
}