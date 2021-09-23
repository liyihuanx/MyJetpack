package com.liyihuanx.myjetpack.adapter

import android.graphics.Color
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelperEx
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liyihuanx.module_base.adapter.BaseDelegateAdapter
import com.liyihuanx.myjetpack.R

/**
 * @author created by liyihuanx
 * @date 2021/9/23
 * @description: 类的描述
 */
class OnePlusNAdapter : BaseDelegateAdapter<Int, BaseViewHolder>(R.layout.item) {

    override fun convert(holder: BaseViewHolder, item: Int) {
        holder.setText(R.id.Item, "$item")
    }

    override val layoutHelper: LayoutHelper by lazy {
        OnePlusNLayoutHelperEx().also {
            it.bgColor = Color.GRAY // 设置背景颜色
            it.aspectRatio = 2f // 设置设置布局内每行布局的宽与高的比
//            it.setPadding(20, 20, 20, 20) // 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
//            it.setMargin(20, 20, 20, 20) // 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
//            it.itemCount = 2 // 设置布局里Item个数
        }
    }

}