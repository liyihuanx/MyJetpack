package com.liyihuanx.module_ui.tab.bottom

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.liyihuanx.module_ui.R
import kotlinx.android.synthetic.main.default_bottom_tab.view.*

/**
 * @author created by liyihuanx
 * @date 2021/8/27
 * @description: 每个BottomView初始化
 */
class BottomTabView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr),
    IBottomTab {

    private lateinit var tabInfo: BottomTabBean
    private var defaultSelectColor: Int = 0
    private var defaultNormalColor: Int = 0

    init {
        LayoutInflater.from(getContext()).inflate(R.layout.default_bottom_tab, this)
    }


    /**
     * 传入底部tab信息
     */
    override fun setTabInfo(
        info: BottomTabBean,
        defaultNormalColor: Int,
        defaultSelectColor: Int
    ) {
        this.tabInfo = info
        this.defaultSelectColor = defaultSelectColor
        this.defaultNormalColor = defaultNormalColor

        // 拿到信息后初始化底部tab栏目
        inflateBottomView(selected = false, isInit = true)
    }

    /**
     * 点击事件
     * 1,2,3,4
     */
    override fun onTabSelectedChange(
        index: Int,
        prevInfo: BottomTabBean?,
        nextInfo: BottomTabBean
    ) {
        if (prevInfo !== tabInfo && nextInfo !== tabInfo || prevInfo === nextInfo) {
            return
        }
        if (prevInfo === tabInfo) {
            inflateBottomView(selected = false, isInit = false)
        } else {
            inflateBottomView(selected = true, isInit = false)
        }
    }

    /**
     * selected 是否点击
     * hasInit 是否需要初始化
     * TODO 以后修改成可以统一设置TextView颜色和字体的
     */
    private fun inflateBottomView(selected: Boolean, isInit: Boolean) {
        // 是否是初始化阶段
        if (isInit) {
            tabInfo.let {
                // 设置了字体的话，设置字体
                it.textFont?.let { textFont ->
                    Typeface.createFromAsset(context.assets, textFont.toString())
                }
                tvTabName.text = it.itemName

                // 是否 默认选中
                if (it.start == true) {
                    tvTabName.setTextColor(getColor(tabInfo.selectColor) ?: defaultSelectColor)
                    ivTab.setImageResource(tabInfo.selectIcon ?: tabInfo.normalIcon)
                } else {
                    // 设置起初的颜色
                    tvTabName.setTextColor(getColor(it.normalColor) ?: defaultNormalColor)
                    // 设置起初的icon
                    ivTab.setImageResource(it.normalIcon)
                }
            }
            return
        }

        if (selected) {
            tvTabName.setTextColor(getColor(tabInfo.selectColor) ?: defaultSelectColor)
            // 选中的Icon可以为空，因为有些在中间的icon可以为一个固定的那种
            ivTab.setImageResource(tabInfo.selectIcon ?: tabInfo.normalIcon)
        } else {
            tvTabName.setTextColor(getColor(tabInfo.normalColor) ?: defaultNormalColor)
            ivTab.setImageResource(tabInfo.normalIcon)
        }
    }


    private fun getColor(colorId: Int?): Int? {
        // 返回null
        return colorId?.let { context.resources.getColor(it) }
    }
}