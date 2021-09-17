package com.liyihuanx.module_base.refresh

import android.content.Context
import android.graphics.Bitmap
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.StringRes
import com.liyihuanx.module_base.R
import kotlinx.android.synthetic.main.view_custom_empty.view.*
import liyihuan.app.android.lazyfragment.refresh.IEmptyView

/**
 * @author created by liyihuanx
 * @date 2021/9/16
 * @description: 类的描述
 */
class CommonEmptyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr),IEmptyView {

    private var mErrorState = 0
    private var clickEnable = true
    private var strNoDataContent = ""


    init {
        LayoutInflater.from(context).inflate(R.layout.view_custom_empty, this)
        visibility = GONE
//        setOnClickListener(this)
    }

    override fun getContentView(): View {
        return this
    }

    /**
     * 根据状态設置当前view
     */
    override fun setErrorType(type: Int) {
        visibility = View.VISIBLE
        when (type) {
            IEmptyView.NETWORK_ERROR -> {
                visibility = VISIBLE
                mErrorState = IEmptyView.NETWORK_ERROR
                emptyText.text = context.getString(R.string.netError)
                img.setBackgroundResource(R.drawable.pic_empty_network)
                img.visibility = VISIBLE
                clickEnable = true
            }
            IEmptyView.NODATA -> {
                visibility = VISIBLE
                mErrorState = IEmptyView.NODATA
                img.setBackgroundResource(R.drawable.pic_empty)
                img.visibility = VISIBLE
                refreshEmptyView()
                clickEnable = true
            }
            IEmptyView.HIDE_LAYOUT -> visibility = GONE

        }
    }


    /**
     * 设置整个背景
     */
    fun setContentBackground(color: Int) {
        llEmptyContent.background = resources.getDrawable(color)
    }

    /**
     * 設置空视图的图片
     */
    fun setEmptyIcon(imgResource: Int) {
        img.setImageResource(imgResource)
    }


    fun setEmptyIcon(imgResource: Bitmap) {
        img.setImageBitmap(imgResource)
    }

    /**
     * 設置内容
     */
    fun setEmptyTips(@StringRes tipsRes: Int) {
        setEmptyTips(context.getString(tipsRes))
    }

    /**
     * 設置内容
     */
    fun setEmptyTips(noDataContent: String) {
        strNoDataContent = noDataContent
        if (emptyText != null) {
            emptyText.text = strNoDataContent
        }
    }

    private fun refreshEmptyView() {
        emptyText.text = if (TextUtils.isEmpty(strNoDataContent)) context.getString(R.string.emptyData) else strNoDataContent
    }

    /**
     * 获取当前错误状态
     *
     * @return
     */
    fun getErrorState(): Int {
        return mErrorState
    }


    override fun setVisibility(visibility: Int) {
        if (visibility == GONE) {
            mErrorState = IEmptyView.HIDE_LAYOUT
        }
        super.setVisibility(visibility)
    }

}