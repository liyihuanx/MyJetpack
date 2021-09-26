package com.liyihuanx.myjetpack.practice.vlayout.adapter

import android.graphics.Color
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.SingleLayoutHelper
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liyihuanx.module_base.adapter.BaseDelegateAdapter
import com.liyihuanx.myjetpack.R

/**
 * @author created by liyihuanx
 * @date 2021/9/23
 * @description: 类的描述
 */
class SingleLayoutAdapter : BaseDelegateAdapter<Int,BaseViewHolder>(R.layout.item) {
    override val layoutHelper: LayoutHelper by lazy {
        SingleLayoutHelper().also {
            it.bgColor = Color.GRAY;// 设置背景颜色
            it.aspectRatio = 6f;// 设置设置布局内每行布局的宽与高的比
        }
    }

    override fun convert(holder: BaseViewHolder, item: Int) {
        holder.setText(R.id.Item, "$item")
    }

    override fun getDefItemCount(): Int {
        return 1
    }
}