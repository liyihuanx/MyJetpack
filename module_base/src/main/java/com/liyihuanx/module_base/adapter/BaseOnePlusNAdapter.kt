package com.liyihuanx.module_base.adapter

import androidx.annotation.LayoutRes
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelperEx
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @ClassName: OnePlusNAdapter
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/9/22 22:30
 */
abstract class BaseOnePlusNAdapter<T, VH : BaseViewHolder>(
    @LayoutRes private val layoutResId: Int
) : BaseDelegateAdapter<T, VH>(layoutResId) {

    override fun getDefItemCount(): Int {
        if (layoutHelper is OnePlusNLayoutHelperEx){
            if (super.getDefItemCount() > 7) {
                return 7
            }
        }

        if (layoutHelper is OnePlusNLayoutHelper){
            if (super.getDefItemCount() > 5) {
                return 5
            }
        }

        return super.getDefItemCount()
    }
}