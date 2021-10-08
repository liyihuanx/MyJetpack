package com.liyihuanx.module_three

import android.content.Context
import android.graphics.Typeface
import android.util.TypedValue
import androidx.fragment.app.Fragment
import com.liyihuanx.module_three.databinding.FragmentThreeBinding
import com.liyihuanx.module_base.fragment.BaseLazyFragment
import com.liyihuanx.module_base.fragment.MainFragment
import com.liyihuanx.module_base.utils.DisplayUtil
import kotlinx.android.synthetic.main.fragment_three.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView

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

        CommonNavigator(context).run {
            scrollPivotX = 0.7f
            isAdjustMode = true
            adapter = commonNavigatorAdapter
            miThreeTabs.navigator = this
        }

        ViewPagerHelper.bind(miThreeTabs, vpThree)

    }

    override fun observeLiveData() {

    }

    override val getTagName: String
        get() = "ThreeFragment"


    private var tittles = arrayOf("页面一", "页面二")

    private val commonNavigatorAdapter = object : CommonNavigatorAdapter() {
        override fun getCount(): Int {
            return tittles.size
        }

        override fun getTitleView(context: Context, index: Int): IPagerTitleView {
            val simplePagerTitleView = ScaleTransitionPagerTitleView(context)
            simplePagerTitleView.text = tittles[index]

            simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f)
            simplePagerTitleView.typeface = Typeface.DEFAULT_BOLD
            val padding = DisplayUtil.dp2px(6f)
            simplePagerTitleView.setPadding(0, 0, padding, 0)
            simplePagerTitleView.normalColor = resources.getColor(R.color.color_808080)
            simplePagerTitleView.selectedColor = resources.getColor(R.color.color_404040)
            simplePagerTitleView.setOnClickListener { v -> vpThree.currentItem = index }
            return simplePagerTitleView
        }

        override fun getIndicator(context: Context): IPagerIndicator {
            return JetPackIndicator(context)
        }
    }

}