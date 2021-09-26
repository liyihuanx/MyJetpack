package com.liyihuanx.myjetpack.practice.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.math.MathUtils
import com.liyihuanx.module_base.utils.StatusBarUtil
import com.liyihuanx.myjetpack.R

/**
 * @author created by liyihuanx
 * @date 2021/9/26
 * @description: 类的描述
 */
class TitleBarBehavior @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    CoordinatorLayout.Behavior<View>(context, attrs) {
    //滑动内容初始化TransY
    private var contentTransY: Float = context.resources.getDimension(R.dimen.content_trans_y)
    private var topBarHeight: Int = context.resources.getDimension(R.dimen.top_bar_height)
        .toInt() + StatusBarUtil.getStatusBarHeight(context)


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
        //调整TitleBar位置要紧贴Content顶部上面
        adjustPosition(parent, child, dependency)
        //这里只计算Content上滑范围一半的百分比
        val start = (contentTransY + topBarHeight) / 2
        val upPro: Float = (contentTransY - MathUtils.clamp(
            dependency.translationY,
            start,
            contentTransY
        )) / (contentTransY - start)
        child.alpha = 1 - upPro
        return true
    }

    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: View,
        layoutDirection: Int
    ): Boolean {
        //找到Content的依赖引用
        val dependencies = parent.getDependencies(child)
        var dependency: View? = null
        for (view in dependencies) {
            if (view.id == R.id.llContent) {
                dependency = view
                break
            }
        }
        return if (dependency != null) {
            //调整TitleBar位置要紧贴Content顶部上面
            adjustPosition(parent, child, dependency)
            true
        } else {
            false
        }
    }

    private fun adjustPosition(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ) {
        val lp = child.layoutParams as CoordinatorLayout.LayoutParams
        val left = parent.paddingLeft + lp.leftMargin
        val top = (dependency.y - child.measuredHeight + lp.topMargin).toInt()
        val right = child.measuredWidth + left - parent.paddingRight - lp.rightMargin
        val bottom = (dependency.y - lp.bottomMargin).toInt()
        child.layout(left, top, right, bottom)
    }

}