package com.liyihuanx.myjetpack.practice.coordinatorlayout

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liyihuanx.myjetpack.R

/**
 * @author created by liyihuanx
 * @date 2021/9/26
 * @description: 类的描述
 */
class CoAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item) {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.Item, "$item")
    }
}