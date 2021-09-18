package com.liyihuan.module_three

import androidx.fragment.app.Fragment
import com.liyihuan.module_three.databinding.FragmentThreeBinding
import com.liyihuanx.module_base.fragment.MainFragment
import kotlinx.android.synthetic.main.fragment_three.*

/**
 * @ClassName: ThreeFragment
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/9/14 23:00
 */
class ThreeFragment : MainFragment<FragmentThreeBinding>(){
    override fun getLayoutId(): Int {
        return R.layout.fragment_three
    }

    override fun initViewOrData() {
        val fragmentsList = arrayListOf<Fragment>(Three1Fragment(),Three2Fragment())
        val homeViewPagerAdapter = HomeViewPagerAdapter(childFragmentManager, fragmentsList)
        vpThree.adapter = homeViewPagerAdapter
    }

    override fun observeLiveData() {

    }

    override val getTagName: String
        get() = "ThreeFragment"
}