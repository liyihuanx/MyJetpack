package liyihuan.app.android.module_ui.tab.bottom

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.default_bottom_tab.view.*
import liyihuan.app.android.module_ui.R

/**
 * @author created by liyihuanx
 * @date 2021/8/27
 * @description: 每个BottomView初始化
 */
class BottomTabView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), IBottomTab {

    private lateinit var tabInfo: BottomTabBean


    init {
        LayoutInflater.from(getContext()).inflate(R.layout.default_bottom_tab, this)

    }


    /**
     * 传入底部tab信息
     */
    override fun setTabInfo(tabinfo: BottomTabBean) {
        this.tabInfo = tabinfo
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
        if (isInit) {
            tabInfo.let {
                // 设置了字体的话，设置字体
                it.textFont?.let { textFont ->
                    Typeface.createFromAsset(context.assets, textFont.toString())
                }
                // 设置起初的颜色
                tvTabName.setTextColor(getColor(it.normalColor))
                tvTabName.text = it.itemName
                // 设置起初的icon
                ivTab.setImageResource(it.normalIcon)
            }

        }

        if (selected) {
            tvTabName.setTextColor(
                getColor(tabInfo.selectColor)
            )
            // 选中的Icon可以为空，因为有些在中间的icon可以为一个固定的那种
            tabInfo.selectIcon?.let { ivTab.setImageResource(it) }
        } else {
            tvTabName.setTextColor(tabInfo.normalColor)
            ivTab.setImageResource(tabInfo.normalIcon)
        }
    }


    private fun getColor(colorId: Int): Int {
        // 返回null
        return context.resources.getColor(colorId)
    }
}