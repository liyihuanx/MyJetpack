package com.liyihuanx.module_two

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liyihuanx.module_common.bean.ChapterBean

/**
 * @author created by liyihuanx
 * @date 2021/9/16
 * @description: 类的描述
 */
class TwoAdapter : BaseQuickAdapter<ChapterBean, BaseViewHolder>(R.layout.item_two) {

    override fun convert(holder: BaseViewHolder, item: ChapterBean) {
        holder.setText(R.id.tvItem, item.name)
    }
}