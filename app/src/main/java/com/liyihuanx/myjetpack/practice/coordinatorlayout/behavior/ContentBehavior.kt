package com.liyihuanx.myjetpack.practice.coordinatorlayout.behavior

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.OverScroller
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.liyihuanx.myjetpack.R

/**
 * @author created by liyihuanx
 * @date 2021/9/26
 * @description: 类的描述
 */
class ContentBehavior @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    CoordinatorLayout.Behavior<View>(context, attrs) {

    private var animDuration: Long

    //topBar内容高度
    private var topBarHeight: Int =
        context.resources.getDimensionPixelOffset(com.liyihuanx.module_base.R.dimen.abc_action_bar_default_height_material)

    //滑动内容初始化TransY
    private var contentTransY: Float

    //下滑时终点值
    private var downEndY: Float

    // 收起内容时执行的动画
    private var restoreAnimator: ValueAnimator = ValueAnimator()

    //Content部分
    private var mLlContent: View? = null

    //fling是否从折叠状态发生的
    private var flingFromCollapsing = false


    init {
        // 给content做上移的动画
        restoreAnimator.addUpdateListener { animation ->
            mLlContent?.translationY = animation.animatedValue as Float
        }

        val obtainStyledAttributes = context.obtainStyledAttributes(
            attrs,
            R.styleable.ContentBehavior
        )
        downEndY =
            obtainStyledAttributes.getDimension(R.styleable.ContentBehavior_contentDownMaxY, 0f)
        contentTransY =
            obtainStyledAttributes.getDimension(R.styleable.ContentBehavior_contentTranslateY, 0f)
        animDuration =
            obtainStyledAttributes.getInt(R.styleable.ContentBehavior_anim_duration, 0).toLong()
        obtainStyledAttributes.recycle()

    }

    override fun onMeasureChild(
        parent: CoordinatorLayout,
        child: View,
        parentWidthMeasureSpec: Int,
        widthUsed: Int,
        parentHeightMeasureSpec: Int,
        heightUsed: Int
    ): Boolean {
        val childLpHeight = child.layoutParams.height
        if (childLpHeight == ViewGroup.LayoutParams.MATCH_PARENT || childLpHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
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
        // 绑定Content View, 为了设置动画效果
        mLlContent = child
        // 设置偏移量
        mLlContent?.translationY = contentTransY
        return super.onLayoutChild(parent, child, layoutDirection)
    }


    /**
     * 对NestedScrollingChild发起嵌套滑动作出应答
     * @param child 布局中包含下面target的直接父View
     * @param target 发起嵌套滑动的NestedScrollingChild的View
     * @param axes 滑动方向
     * @return 返回NestedScrollingParent是否配合处理嵌套滑动
     */
    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        //只接受内容View的垂直滑动
        return (directTargetChild.id == R.id.llContent
                && axes == ViewCompat.SCROLL_AXIS_VERTICAL)
    }

    /**
     * NestedScrollingChild滑动完成后将滑动值分发给NestedScrollingParent回调此方法
     * @param target 同上
     * @param dxConsumed 水平方向消费的距离
     * @param dyConsumed 垂直方向消费的距离
     * @param dxUnconsumed 水平方向剩余的距离
     * @param dyUnconsumed 垂直方向剩余的距离
     */
//    override fun onNestedScroll(
//        coordinatorLayout: CoordinatorLayout,
//        child: View,
//        target: View,
//        dxConsumed: Int,
//        dyConsumed: Int,
//        dxUnconsumed: Int,
//        dyUnconsumed: Int,
//        type: Int,
//        consumed: IntArray
//    ) {
//        super.onNestedScroll(
//            coordinatorLayout,
//            child,
//            target,
//            dxConsumed,
//            dyConsumed,
//            dxUnconsumed,
//            dyUnconsumed,
//            ViewCompat.TYPE_TOUCH,
//            consumed
//        )
//    }

    /**
     * NestedScrollingChild滑动完之前将滑动值分发给NestedScrollingParent回调此方法
     * @param target 同上
     * @param dx 水平方向的距离
     * @param dy 水平方向的距离
     * @param consumed 返回NestedScrollingParent是否消费部分或全部滑动值
     */
    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        // child.translationY 原本距离顶部的距离，上滑dy>0, content里面的view往上走，
        // translationY会越来越小，到和顶部栏一样的高度则不再上移动。会走child的滑动事件

        // 当前contentView在Y轴的偏移值
        val transY = child.translationY - dy
        //处理上滑
        if (dy > 0) {
            // 还可以上移动
            if (transY >= topBarHeight) {
                translationByConsume(child, transY, consumed, dy.toFloat())
            } else {
                // child消费剩下的dy
                translationByConsume(
                    child,
                    topBarHeight.toFloat(),
                    consumed,
                    child.translationY - topBarHeight
                )
            }
        }

        if (dy < 0 && !target.canScrollVertically(-1)) {
            //下滑时处理Fling,折叠时下滑Recycler(或NestedScrollView) Fling滚动到contentTransY停止Fling
            if (type == ViewCompat.TYPE_NON_TOUCH && transY >= contentTransY && flingFromCollapsing) {
                flingFromCollapsing = false
                translationByConsume(child, contentTransY, consumed, dy.toFloat())
//                stopViewScroll(target)
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
        //如果是从初始状态转换到展开状态过程触发收起动画
        if (child.translationY > contentTransY) {
            restore()
        }
        super.onStopNestedScroll(coordinatorLayout, child, target, type)
    }

    /**
     * NestedScrollingChild在惯性滑动之前,将惯性滑动的速度分发给NestedScrollingParent
     * @param target 同上
     * @param velocityX 同上
     * @param velocityY 同上
     * @return 返回NestedScrollingParent是否消费全部惯性滑动
     */
    override fun onNestedPreFling(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        flingFromCollapsing = child.translationY <= contentTransY
        return false
    }

    /**
     * NestedScrollingParent配合处理嵌套滑动回调此方法
     * @param child 同上
     * @param target 同上
     * @param axes 同上
     */
    override fun onNestedScrollAccepted(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ) {
        // 动画未执行完毕手指再落下滑动时，取消当前动画
        if (restoreAnimator.isStarted) {
            restoreAnimator.cancel()
        }
        super.onNestedScrollAccepted(
            coordinatorLayout,
            child,
            directTargetChild,
            target,
            axes,
            type
        )
    }


    /**
     * 停止content里滑动的view
     */
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

    /**
     * @param view 做平移的view
     * @param translationY 当前view在Y的偏移值
     * @param consumed 使用了的dx,dy集合
     * @param consumedDy
     */
    private fun translationByConsume(
        view: View,
        translationY: Float,
        consumed: IntArray,
        consumedDy: Float
    ) {
        consumed[1] = consumedDy.toInt()
        view.translationY = translationY
    }


    private fun restore() {
        if (restoreAnimator.isStarted) {
            restoreAnimator.cancel()
            restoreAnimator.removeAllListeners()
        }
        restoreAnimator.setFloatValues(mLlContent!!.translationY, contentTransY)
        restoreAnimator.duration = animDuration
        restoreAnimator.start()
    }
}