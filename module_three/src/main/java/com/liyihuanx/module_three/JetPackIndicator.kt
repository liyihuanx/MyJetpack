package com.liyihuanx.module_three

import android.content.Context
import android.graphics.*
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import net.lucode.hackware.magicindicator.FragmentContainerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.model.PositionData

/**
 * @author created by liyihuanx
 * @date 2021/10/8
 * @description: 类的描述
 */
class JetPackIndicator(context: Context) : View(context), IPagerIndicator {

    val MODE_MATCH_EDGE = 0 // 直线宽度 == title宽度 - 2 * mXOffset

    val MODE_WRAP_CONTENT = 1 // 直线宽度 == title内容宽度 - 2 * mXOffset

    val MODE_EXACTLY = 2 // 直线宽度 == mLineWidth

    private val mMode = MODE_WRAP_CONTENT // 默认为MODE_MATCH_EDGE模式

    // 控制动画
    private val mStartInterpolator: Interpolator = LinearInterpolator()
    private val mEndInterpolator: Interpolator = LinearInterpolator()

    // 相对于底部的偏移量，如果你想让直线位于title上方，设置它即可
    private var mYOffset = 0f

    private var mLineHeight = 0f
    private val mXOffset = 0f
    private var mLineWidth = 0f
    private val mRoundRadius = 16f

    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mPositionDataList: List<PositionData>? = null
    private val mColors: List<Int>? = null

    private val mLineRect = RectF()

    init {
        mPaint.style = Paint.Style.FILL
        mLineHeight = UIUtil.dip2px(context, 8.0).toFloat()
        mLineWidth = UIUtil.dip2px(context, 10.0).toFloat()
        mYOffset = UIUtil.dip2px(context, 7.0).toFloat()

    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(mLineRect, mRoundRadius, mRoundRadius, mPaint);
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (mPositionDataList.isNullOrEmpty()) {
            return
        }
        // 计算锚点位置
        val current = FragmentContainerHelper.getImitativePositionData(mPositionDataList, position)
        val next = FragmentContainerHelper.getImitativePositionData(mPositionDataList, position + 1)

        val leftX: Float
        val nextLeftX: Float
        val rightX: Float
        val nextRightX: Float
        when (mMode) {
            MODE_MATCH_EDGE -> {
                leftX = current.mLeft + mXOffset
                nextLeftX = next.mLeft + mXOffset
                rightX = current.mRight - mXOffset
                nextRightX = next.mRight - mXOffset
            }
            MODE_WRAP_CONTENT -> {
                leftX = current.mContentLeft + mXOffset
                nextLeftX = next.mContentLeft + mXOffset
                rightX = current.mContentRight - mXOffset
                nextRightX = next.mContentRight - mXOffset
            }
            else -> {    // MODE_EXACTLY
                leftX = current.mLeft + (current.width() - mLineWidth) / 2
                nextLeftX = next.mLeft + (next.width() - mLineWidth) / 2
                rightX = current.mLeft + (current.width() + mLineWidth) / 2
                nextRightX = next.mLeft + (next.width() + mLineWidth) / 2
            }
        }

        mLineRect.left =
            leftX + (nextLeftX - leftX) * mStartInterpolator.getInterpolation(positionOffset) - UIUtil.dip2px(
                context, 3.0
            )
        mLineRect.right =
            rightX + (nextRightX - rightX) * mEndInterpolator.getInterpolation(positionOffset) + UIUtil.dip2px(
                context, 3.0
            )
        mLineRect.top = height - mLineHeight - mYOffset
        mLineRect.bottom = height - mYOffset
        val linearGradient = LinearGradient(
            mLineRect.left, mLineRect.top, mLineRect.right, mLineRect.bottom,
            Color.parseColor("#F3455B"), Color.parseColor("#00000000"),
            Shader.TileMode.CLAMP
        )
        mPaint.shader = linearGradient
        invalidate()
    }

    override fun onPageSelected(position: Int) {
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPositionDataProvide(dataList: MutableList<PositionData>?) {
        mPositionDataList = dataList
    }
}