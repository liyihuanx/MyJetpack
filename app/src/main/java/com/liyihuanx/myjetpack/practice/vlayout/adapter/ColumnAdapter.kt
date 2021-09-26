package com.liyihuanx.myjetpack.practice.vlayout.adapter

import android.graphics.Color
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.ColumnLayoutHelper
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liyihuanx.module_base.adapter.BaseDelegateAdapter
import com.liyihuanx.myjetpack.R

/**
 * @ClassName: ColumnAdapter
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/9/22 23:24
 */
class ColumnAdapter : BaseDelegateAdapter<Int, BaseViewHolder>(R.layout.item) {

    override fun convert(holder: BaseViewHolder, item: Int) {
        holder.setText(R.id.Item, "$item")
    }

    override val layoutHelper: LayoutHelper by lazy {
        ColumnLayoutHelper().also {
            it.itemCount = 3 // 设置布局里Item个数
            it.bgColor = Color.GRAY // 设置背景颜色
            it.aspectRatio = 6f // 设置设置布局内每行布局的宽与高的比
            it.setWeights(floatArrayOf(20f, 60f, 20f)) // 设置该行每个Item占该行总宽度的比例
        }
    }


}