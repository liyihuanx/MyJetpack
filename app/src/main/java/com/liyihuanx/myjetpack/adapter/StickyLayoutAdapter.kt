package com.liyihuanx.myjetpack.adapter

import android.graphics.Color
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.StickyLayoutHelper
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liyihuanx.module_base.adapter.BaseDelegateAdapter
import com.liyihuanx.myjetpack.R

/**
 * @ClassName: StickyLayoutAdapter
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/9/23 21:50
 */
class StickyLayoutAdapter : BaseDelegateAdapter<Int, BaseViewHolder>(R.layout.item) {
    override val layoutHelper: LayoutHelper by lazy {
        StickyLayoutHelper().also {
            it.bgColor = Color.RED// 设置背景颜色
            it.aspectRatio = 3f// 设置设置布局内每行布局的宽与高的比

            it.setStickyStart(true) // true = 组件吸在顶部 false = 组件吸在底部
            it.setOffset(100);// 设置吸边位置的偏移量

        }
    }

    override fun convert(holder: BaseViewHolder, item: Int) {
        holder.setText(R.id.Item, "Stick")
            .itemView.setBackgroundResource(R.color.colorPrimary)
    }
}