package com.liyihuanx.myjetpack

import android.util.Log
import androidx.fragment.app.Fragment
import com.liyihuanx.module_base.activity.BaseActivity
import com.liyihuanx.module_home.HomeFragment
import com.liyihuanx.myjetpack.databinding.ActivityMainBinding
import com.liyihuanx.myjetpack.navigation.MineFragment
import com.liyihuanx.myjetpack.navigation.ThreeFragment
import com.liyihuanx.myjetpack.navigation.TwoFragment
import kotlinx.android.synthetic.main.activity_main.*
import liyihuan.app.android.module_ui.tab.bottom.BottomTabBean
import liyihuan.app.android.module_ui.tab.bottom.IBottomLayout


class MainActivity : BaseActivity<ActivityMainBinding>() {


    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun bindLayoutData() {
//        val itemName: String,
//        val selectIcon: Int? = null,
//        val normalIcon: Int,
//        val selectColor: Int? = null,
//        val normalColor: Int? = null,
//        val textFont: Int? = null
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
            }
        })


        val arrayListOfFragment = arrayListOf<Fragment>(
            HomeFragment(), TwoFragment(), ThreeFragment(), MineFragment()
        )
        val mainAdapter = MainAdapter(arrayListOfFragment, this)
        mainVp.adapter = mainAdapter
        mainVp.isUserInputEnabled = false
    }

}