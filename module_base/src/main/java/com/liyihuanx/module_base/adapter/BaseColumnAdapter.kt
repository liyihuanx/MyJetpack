package com.liyihuanx.module_base.adapter

import androidx.annotation.LayoutRes
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @author created by liyihuanx
 * @date 2021/9/23
 * @description: 类的描述
 */
abstract class BaseColumnAdapter<T, VH : BaseViewHolder>(
    @LayoutRes private val layoutResId: Int
) : BaseDelegateAdapter<T, VH>(layoutResId)  {

    override fun getDefItemCount(): Int {
        if (layoutHelper.itemCount != 0 && super.getDefItemCount() > layoutHelper.itemCount) {
            return layoutHelper.itemCount
        }
        return super.getDefItemCount()
    }
}