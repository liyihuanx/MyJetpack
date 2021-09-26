package com.liyihuanx.myjetpack.practice.vlayout.adapter

import android.graphics.Color
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.GridLayoutHelper
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liyihuanx.module_base.adapter.BaseDelegateAdapter
import com.liyihuanx.myjetpack.R

/**
 * @ClassName: GridLayoutAdapter
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/9/23 21:26
 */
class GridLayoutAdapter : BaseDelegateAdapter<Int, BaseViewHolder>(R.layout.item) {
    override val layoutHelper: LayoutHelper by lazy {
        GridLayoutHelper(6).also {

            // 公共属性
            it.bgColor = Color.GRAY // 设置背景颜色
            it.aspectRatio = 6f // 设置设置布局内每行布局的宽与高的比


//            it.setWeights(floatArrayOf(20f, 60f, 20f)) //设置每行中 每个网格宽度 占 每行总宽度 的比例
            it.vGap = 20 // 控制子元素之间的垂直间距
            it.hGap = 20 // 控制子元素之间的水平间距
            it.setAutoExpand(true) //是否自动填充空白区域
            // 通过自定义SpanSizeLookup来控制某个Item的占网格个数
            it.setSpanSizeLookup(object : GridLayoutHelper.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position - startPosition > 7) {
                        3 // 第7个位置后,每个Item占3个网格
                    } else {
                        2 // 第7个位置前,每个Item占2个网格
                    }
                }
            })
        }
    }

    override fun convert(holder: BaseViewHolder, item: Int) {
        holder.setText(R.id.Item, "$item")
    }


}