package com.liyihuanx.module_base.adapter

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * @author created by liyihuanx
 * @date 2021/9/23
 * @description: 类的描述
 */
abstract class BaseDataBindingAdapter<T, DB : ViewDataBinding> @JvmOverloads constructor(
    @LayoutRes private val layoutResId: Int,
    data: MutableList<T>? = null
) : BaseDelegateAdapter<T, BaseDataBindingHolder<DB>>(layoutResId, data) {

    private lateinit var mHolder: BaseDataBindingHolder<DB>

    override fun convert(holder: BaseDataBindingHolder<DB>, item: T) {
        mHolder = holder
        convertData(holder.dataBinding, item)
    }

    abstract fun convertData(mBinding: DB?, item: T)

    fun getHolder(): BaseDataBindingHolder<DB> {
        return mHolder
    }

}