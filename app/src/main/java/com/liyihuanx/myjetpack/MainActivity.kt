package com.liyihuanx.myjetpack

import com.liyihuanx.module_base.activity.BaseActivity
import com.liyihuanx.myjetpack.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import liyihuan.app.android.module_ui.tab.bottom.BottomTabBean


class MainActivity : BaseActivity<ActivityMainBinding>() {


    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun bindLayoutData() {
        mBinding.userInfo = UserInfoBean()
//        val itemName: String,
//        val selectIcon: Int? = null,
//        val normalIcon: Int,
//        val selectColor: Int? = null,
//        val normalColor: Int? = null,
//        val textFont: Int? = null
        val arrayListOf = arrayListOf<BottomTabBean>(
            BottomTabBean(
                "首页1", R.drawable.tabbar_account_selected, R.drawable.tabbar_account
            ),
            BottomTabBean(
                "首页2", R.drawable.tabbar_account_selected, R.drawable.tabbar_account
            ),
            BottomTabBean(
                "首页3", R.drawable.tabbar_account_selected, R.drawable.tabbar_account
            ),
            BottomTabBean(
                "首页4", R.drawable.tabbar_account_selected, R.drawable.tabbar_account
            )
        )
        tabLayout.bindBottomTabData(arrayListOf)
    }

}