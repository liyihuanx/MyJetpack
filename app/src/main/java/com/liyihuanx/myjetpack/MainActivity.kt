package com.liyihuanx.myjetpack

import com.liyihuanx.module_base.activity.BaseActivity
import com.liyihuanx.myjetpack.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import liyihuan.app.android.module_ui.tab.bottom.HiTabBottomInfo


class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val infoList: ArrayList<HiTabBottomInfo<*>> = ArrayList()


    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun bindLayoutData() {
        mBinding.userInfo = UserInfoBean()
        val defaultColor: Int = this.resources.getColor(R.color.tabBottomDefaultColor)
        val tintColor: Int = this.resources.getColor(R.color.tabBottomTintColor)


        val homeInfo = HiTabBottomInfo<Int>(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.if_home),
            null,
            defaultColor,
            tintColor
        )

        val infoFavorite = HiTabBottomInfo(
            "收藏",
            "fonts/iconfont.ttf",
            getString(R.string.if_favorite),
            null,
            defaultColor,
            tintColor
        )

        val infoProfile = HiTabBottomInfo(
            "我的",
            "fonts/iconfont.ttf",
            getString(R.string.if_profile),
            null,
            defaultColor,
            tintColor
        )

        infoList.add(homeInfo)
        infoList.add(infoFavorite)
        infoList.add(infoProfile)
        tabBottomLayout.inflateInfo(infoList)

    }

}