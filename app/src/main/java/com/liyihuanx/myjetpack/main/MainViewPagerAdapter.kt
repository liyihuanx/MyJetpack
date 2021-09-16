package com.liyihuanx.myjetpack.main

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*

/**
 * @ClassName: HomeViewPagerAdapter
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2020/10/18 16:20
 */
class MainViewPagerAdapter(
    private val mFm: FragmentManager,
    private val fragmentsList: ArrayList<Fragment>
) : FragmentPagerAdapter(mFm) {
    override fun getItem(position: Int): Fragment {
        return fragmentsList[position]
    }

    override fun getCount(): Int {
        return fragmentsList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        //得到缓存的fragment
        val fragment = super.instantiateItem(container, position) as Fragment
        mFm.beginTransaction().show(fragment).commitAllowingStateLoss()
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val fragment = fragmentsList[position]
        mFm.beginTransaction().hide(fragment).commitAllowingStateLoss()
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return super.isViewFromObject(view, `object`)
    }
}