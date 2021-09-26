package com.liyihuanx.myjetpack.practice.behavior

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.OverScroller
import androidx.annotation.NonNull
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.liyihuanx.module_base.utils.StatusBarUtil
import com.liyihuanx.myjetpack.R

/**
 * @author created by liyihuanx
 * @date 2021/9/26
 * @description: 类的描述
 */
class ContentBehavior @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    CoordinatorLayout.Behavior<View>(context, attrs) {

    private val ANIM_DURATION_FRACTION = 200L

    //topBar内容高度
    private var topBarHeight: Int

    //滑动内容初始化TransY
    private var contentTransY: Float

    //下滑时终点值
    private var downEndY: Float

    // 收起内容时执行的动画
    private var restoreAnimator: ValueAnimator

    //Content部分
    private var mLlContent: View? = null
    private var flingFromCollaps = false //fling是否从折叠状态发生的


    init {
        val statusBarHeight = StatusBarUtil.getStatusBarHeight(context)
        topBarHeight =
            context.resources.getDimension(R.dimen.top_bar_height).toInt() + statusBarHeight
        contentTransY = context.resources.getDimension(R.dimen.content_trans_y)
        downEndY = context.resources.getDimension(R.dimen.content_trans_down_end_y)

        restoreAnimator = ValueAnimator()
        restoreAnimator.addUpdateListener(AnimatorUpdateListener { animation ->
            translation(
                mLlContent,
                animation.animatedValue as Float
            )
        })
    }

    override fun onMeasureChild(
        @NonNull parent: CoordinatorLayout, child: View,
        parentWidthMeasureSpec: Int, widthUsed: Int, parentHeightMeasureSpec: Int,
        heightUsed: Int
    ): Boolean {
        val childLpHeight = child.layoutParams.height
        if (childLpHeight == ViewGroup.LayoutParams.MATCH_PARENT
            || childLpHeight == ViewGroup.LayoutParams.WRAP_CONTENT
        ) {
            //先获取CoordinatorLayout的测量规格信息，若不指定具体高度则使用CoordinatorLayout的高度
            var availableHeight = View.MeasureSpec.getSize(parentHeightMeasureSpec)
            if (availableHeight == 0) {
                availableHeight = parent.height
            }
            //设置Content部分高度
            val height = availableHeight - topBarHeight
            val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                height,
                if (childLpHeight == ViewGroup.LayoutParams.MATCH_PARENT) View.MeasureSpec.EXACTLY else View.MeasureSpec.AT_MOST
            )
            //执行指定高度的测量，并返回true表示使用Behavior来代理测量子View
            parent.onMeasureChild(
                child, parentWidthMeasureSpec,
                widthUsed, heightMeasureSpec, heightUsed
            )
            return true
        }
        return false
    }

    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: View,
        layoutDirection: Int
    ): Boolean {
        val handleLayout = super.onLayoutChild(parent!!, child, layoutDirection)
        //绑定Content View
        mLlContent = child
        return handleLayout
    }


    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return super.onStartNestedScroll(
            coordinatorLayout,
            child,
            directTargetChild,
            target,
            axes,
            ViewCompat.TYPE_TOUCH
        )
    }


    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        super.onNestedScroll(
            coordinatorLayout,
            child,
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            ViewCompat.TYPE_TOUCH,
            consumed
        )
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {

        val transY = child.translationY - dy
        //处理上滑
        if (dy > 0) {
            if (transY >= topBarHeight) {
                translationByConsume(child, transY, consumed, dy.toFloat())
            } else {
                translationByConsume(
                    child, topBarHeight.toFloat(), consumed,
                    child.translationY - topBarHeight
                )
            }
        }

        if (dy < 0 && !target.canScrollVertically(-1)) {
            //下滑时处理Fling,折叠时下滑Recycler(或NestedScrollView) Fling滚动到contentTransY停止Fling
            if (type == ViewCompat.TYPE_NON_TOUCH && transY >= contentTransY && flingFromCollaps) {
                flingFromCollaps = false
                translationByConsume(child, contentTransY, consumed, dy.toFloat())
                stopViewScroll(target)
                return
            }

            //处理下滑
            if (transY >= topBarHeight && transY <= downEndY) {
                translationByConsume(child, transY, consumed, dy.toFloat())
            } else {
                translationByConsume(child, downEndY, consumed, downEndY - child.translationY)
                stopViewScroll(target)
            }
        }

    }

    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        type: Int
    ) {
        super.onStopNestedScroll(coordinatorLayout, child, target, ViewCompat.TYPE_TOUCH)
    }


    override fun onNestedPreFling(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        flingFromCollaps = child.translationY <= contentTransY
        return false
    }

    //---NestedScrollingParent2---//

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int
    ): Boolean {
        //只接受内容View的垂直滑动
        return (directTargetChild.id == R.id.llContent
                && axes == ViewCompat.SCROLL_AXIS_VERTICAL)
    }


    override fun onNestedScrollAccepted(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int
    ) {
        if (restoreAnimator.isStarted) {
            restoreAnimator.cancel()
        }
    }

    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View
    ) {
        //如果是从初始状态转换到展开状态过程触发收起动画
        if (child.translationY > contentTransY) {
            restore()
        }
    }


    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray
    ) {
        onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, ViewCompat.TYPE_TOUCH)
    }


    private fun stopViewScroll(target: View) {
        if (target is RecyclerView) {
            (target as RecyclerView).stopScroll()
        }
        if (target is NestedScrollView) {
            try {
                val clazz: Class<out NestedScrollView?> = (target as NestedScrollView)::class.java
                val mScroller = clazz.getDeclaredField("mScroller")
                mScroller.isAccessible = true
                val overScroller = mScroller[target] as OverScroller
                overScroller.abortAnimation()
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
    }

    private fun translationByConsume(
        view: View,
        translationY: Float,
        consumed: IntArray,
        consumedDy: Float
    ) {
        consumed[1] = consumedDy.toInt()
        view.translationY = translationY
    }

    private fun translation(view: View?, translationY: Float) {
        view?.translationY = translationY
    }

    private fun restore() {
        if (restoreAnimator.isStarted) {
            restoreAnimator.cancel()
            restoreAnimator.removeAllListeners()
        }
        restoreAnimator.setFloatValues(mLlContent!!.translationY, contentTransY)
        restoreAnimator.duration = ANIM_DURATION_FRACTION
        restoreAnimator.start()
    }
}