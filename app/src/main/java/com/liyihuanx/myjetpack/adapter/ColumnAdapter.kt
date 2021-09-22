package com.liyihuanx.myjetpack.adapter

import com.alibaba.android.vlayout.LayoutHelper
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liyihuanx.myjetpack.R

/**
 * @ClassName: ColumnAdapter
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/9/22 23:24
 */
class ColumnAdapter(override val layoutHelper: LayoutHelper) :
    BaseDelegateAdapter<Int, BaseViewHolder>(R.layout.item) {


    override fun convert(holder: BaseViewHolder, item: Int) {
        holder.setText(R.id.Item, "$item")
    }
}