package com.liyihuanx.module_base.adapter

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager

/**
 * @ClassName: MyOnItemClickListener
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/9/22 22:20
 */
interface OnItemClickListener {
    fun onItemClick(adapter: BaseDelegateAdapter<*, *>, view: View, position: Int)
}


interface OnItemLongClickListener {
    fun onItemLongClick(adapter: BaseDelegateAdapter<*, *>, view: View, position: Int): Boolean
}

interface OnItemChildClickListener {
    fun onItemChildClick(adapter: BaseDelegateAdapter<*, *>, view: View, position: Int)
}

interface OnItemChildLongClickListener {

    fun onItemChildLongClick(adapter: BaseDelegateAdapter<*, *>, view: View, position: Int): Boolean
}

interface GridSpanSizeLookup {
    fun getSpanSize(gridLayoutManager: GridLayoutManager, viewType: Int, position: Int): Int
}

interface BaseListenerImp {
    /**
     * Register a callback to be invoked when an item in this RecyclerView has
     * been clicked.
     *
     * @param listener The callback that will be invoked.
     */
    fun setOnItemClickListener(listener: OnItemClickListener?)
    fun setOnItemLongClickListener(listener: OnItemLongClickListener?)
    fun setOnItemChildClickListener(listener: OnItemChildClickListener?)
    fun setOnItemChildLongClickListener(listener: OnItemChildLongClickListener?)
    fun setGridSpanSizeLookup(spanSizeLookup: GridSpanSizeLookup?)
}