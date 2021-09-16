package com.liyihuanx.myjetpack.main

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.liyihuan.module_three.ThreeFragment
import com.liyihuan.module_two.TwoFragment
import com.liyihuanx.module_base.activity.BaseActivity
import com.liyihuanx.module_base.fragment.LazyRecyclerFragment
import com.liyihuanx.module_base.utils.StatusBarUtil
import com.liyihuanx.module_home.HomeFragment
import com.liyihuanx.module_mine.MineFragment
import com.liyihuanx.module_ui.tab.bottom.BottomTabBean
import com.liyihuanx.module_ui.tab.bottom.IBottomLayout
import com.liyihuanx.myjetpack.R
import com.liyihuanx.myjetpack.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : BaseActivity<ActivityMainBinding>() {


    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun bindViewOrData() {
        StatusBarUtil.setDarkContentStatusBar(this)

        val arrayListOfFragment = arrayListOf<Fragment>(
            HomeFragment(), TwoFragment(), ThreeFragment(), MineFragment()
        )

        val arrayListOf = arrayListOf<BottomTabBean>(
            BottomTabBean(
                "首页1",
                R.drawable.tab_home_s,
                R.drawable.tab_home_n,
                start = true
            ),
            BottomTabBean(
                "首页2",
                R.drawable.tab_square_s,
                R.drawable.tab_square_n
            ),
            BottomTabBean(
                "首页3",
                R.drawable.tab_conversation_s,
                R.drawable.tab_conversation_n
            ),
            BottomTabBean(
                "首页4",
                R.drawable.tab_mine_s,
                R.drawable.tab_mine_n
            ),
        )
        tabLayout.bindBottomTabData(arrayListOf)
        tabLayout.addTabSelectInterceptorListener(object :
            IBottomLayout.OnTabSelectInterceptorListener {

            override fun selectAfterInterceptor(index: Int) {
                mainVp.setCurrentItem(index,false)
                if (arrayListOfFragment[index] is LazyRecyclerFragment<*,*>){
                    (arrayListOfFragment[index] as LazyRecyclerFragment<*,*>).clickRefresh(index)
                }
            }
        })



        //viewpager2
//        val mainAdapter = MainAdapter(arrayListOfFragment, this)
//        mainVp.adapter = mainAdapter
//        // 能不能左右滑动
//        mainVp.isUserInputEnabled = false


        // viewpager
        val viewPagerAdapter =
            MainViewPagerAdapter(
                supportFragmentManager,
                arrayListOfFragment
            )
        mainVp.adapter = viewPagerAdapter
        mainVp.currentItem = 0
        mainVp.offscreenPageLimit = 1

    }


}