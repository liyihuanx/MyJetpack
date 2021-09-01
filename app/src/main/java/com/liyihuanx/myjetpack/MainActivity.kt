package com.liyihuanx.myjetpack

import android.util.Log
import com.liyihuanx.module_base.activity.BaseActivity
import com.liyihuanx.myjetpack.databinding.ActivityMainBinding
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
                R.drawable.tabbar_account_selected,
                R.drawable.tabbar_account,
                R.color.defaultSelectColor,
                R.color.defaultNormalColor,
                true
            ),
            BottomTabBean(
                "首页2",
                R.drawable.tabbar_account_selected,
                R.drawable.tabbar_account,
                R.color.defaultSelectColor,
                R.color.defaultNormalColor
            ),
            BottomTabBean(
                "首页3",
                R.drawable.tabbar_account_selected,
                R.drawable.tabbar_account,
                R.color.defaultSelectColor,
                R.color.defaultNormalColor
            ),
            BottomTabBean(
                "首页4",
                R.drawable.tabbar_account_selected,
                R.drawable.tabbar_account,
                R.color.defaultSelectColor,
                R.color.defaultNormalColor
            ),
        )
        tabLayout.bindBottomTabData(arrayListOf)
//        tabLayout.addTabSelectInterceptorListener(object :
//            IBottomLayout.OnTabSelectInterceptorListener {
//            override fun onTabSelectedInterceptor(
//                index: Int,
//                prevInfo: BottomTabBean?,
//                nextInfo: BottomTabBean
//            ): Boolean {
//                Log.d("QWER", "拦截了点击事件")
//                return true
//            }
//        })
    }

}