package com.liyihuanx.module_ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingComponent
import androidx.viewpager.widget.ViewPager

/**
 * @author created by liyihuanx
 * @date 2021/9/22
 * @description: 类的描述
 */
object DataBindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "error"], requireAll = false)
    fun loadImage(imageView: ImageView, imageUrl: String, error: Int) {

    }

    @JvmStatic
    @BindingAdapter(value = ["canScroll"], requireAll = false)
    fun viewPagerScroll(viewPager: ViewPager, canScroll: Boolean) {
    }
}