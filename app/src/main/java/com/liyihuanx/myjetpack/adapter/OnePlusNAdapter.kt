package com.liyihuanx.myjetpack.adapter

import com.alibaba.android.vlayout.LayoutHelper
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liyihuanx.myjetpack.R

/**
 * @ClassName: OnePlusNAdapter
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/9/22 22:30
 */
class OnePlusNAdapter(override val layoutHelper: LayoutHelper) :
    BaseDelegateAdapter<Int, BaseViewHolder>(R.layout.item) {


    override fun convert(holder: BaseViewHolder, item: Int) {
        holder.setText(R.id.Item, "$item")
    }
}