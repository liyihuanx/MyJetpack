package com.liyihuanx.module_two

import com.liyihuanx.module_common.bean.ChapterBean
import com.liyihuanx.module_two.databinding.ItemTwoBinding
import com.liyihuanx.module_base.adapter.BaseDataBindingQuickAdapter

/**
 * @author created by liyihuanx
 * @date 2021/9/22
 * @description: 类的描述
 */
class TwoDataBindingAdapter : BaseDataBindingQuickAdapter<ChapterBean, ItemTwoBinding>(R.layout.item_two) {

    override fun convertData(mBinding: ItemTwoBinding?, item: ChapterBean) {
        mBinding?.also {
            it.itemTwoData = item
            it.executePendingBindings()
        }
    }
}