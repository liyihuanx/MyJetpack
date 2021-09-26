package com.liyihuanx.myjetpack.practice.vlayout.adapter

import android.graphics.Color
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.FixLayoutHelper
import com.alibaba.android.vlayout.layout.ScrollFixLayoutHelper
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liyihuanx.module_base.adapter.BaseDelegateAdapter
import com.liyihuanx.module_base.utils.DisplayUtil
import com.liyihuanx.myjetpack.R

/**
 * @author created by liyihuanx
 * @date 2021/9/23
 * @description: 类的描述
 */
class ScrollFixLayoutAdapter :  BaseDelegateAdapter<Int, BaseViewHolder>(R.layout.item) {
    override val layoutHelper: LayoutHelper by lazy {
        ScrollFixLayoutHelper(ScrollFixLayoutHelper.BOTTOM_RIGHT, 0, 0).also {
            it.itemCount = 1;// 设置布局里Item个数
            it.bgColor = Color.GRAY;// 设置背景颜色
            it.aspectRatio = 1F;// 设置设置布局内每行布局的宽与高的比

            // fixLayoutHelper特有属性
            it.setAlignType(FixLayoutHelper.BOTTOM_RIGHT);// 设置吸边时的基准位置(alignType)
            it.setX(30);// 设置基准位置的横向偏移量X
            it.setY(50);// 设置基准位置的纵向偏移量Y
            it.showType = ScrollFixLayoutHelper.SHOW_ON_ENTER;// 设置Item的显示模式


        }
    }

    override fun convert(holder: BaseViewHolder, item: Int) {
        holder.setText(R.id.Item, "$item")
        holder.itemView.layoutParams.width = DisplayUtil.dp2px(100f)
        holder.itemView.layoutParams.height = DisplayUtil.dp2px(100f)

    }

    override fun getDefItemCount(): Int {
        return 1
    }

}