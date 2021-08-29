package liyihuan.app.android.module_ui.tab.bottom

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import com.liyihuanx.module_base.utils.DisplayUtil

/**
 * @author created by liyihuanx
 * @date 2021/8/27
 * @description: 加载每一个bottomView
 */
class BottomTabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), IBottomLayout {

    private val TAG_TAB_BOTTOM_LAYOUT = "TAG_TAB_BOTTOM_LAYOUT"

    // 所有的tab的list
    private var bottomTabList = ArrayList<BottomTabBean>()

    // 所有的tabview
    private var bottomTabViewList = HashMap<BottomTabBean, BottomTabView>()

    // 点击事件之前tab的信息
    private var prevTabBeanInfo: BottomTabBean? = null

    private var iBottomViewController = IBottomViewController.DEFAULT

    // 高度
    private val defaultLayoutHeight by lazy {
        DisplayUtil.dp2px(IBottomViewController.defaultLayoutHeight, resources)
    }


    // 每个tab的宽度
    private val tabWidth by lazy {
        DisplayUtil.getDisplayWidthInPx(context) / bottomTabList.size
    }

    var enterPosition: Int = -1

    /**
     * 给外部调用，把底部tab初始化，添加到布局，和数据绑定
     */
    override fun bindBottomTabData(data: ArrayList<BottomTabBean>) {
        // 在本地保存一份
        this.bottomTabList = data
        // 先移除和重置
        for (i in childCount - 1 downTo 1) {
            removeViewAt(i)
        }
        prevTabBeanInfo = null

        // 赋初值
        setPrevTabBeanInfo()
        val flBottomLayout = FrameLayout(context)
        flBottomLayout.tag = TAG_TAB_BOTTOM_LAYOUT

        val defaultLayoutHeight = DisplayUtil.dp2px(IBottomViewController.defaultLayoutHeight, resources)
        val tabWidth = DisplayUtil.getDisplayWidthInPx(context) / bottomTabList.size


        bottomTabList.forEachIndexed { index, bottomTabBean ->
            //用LinearLayout当动态改变child大小后Gravity.BOTTOM会失效
            val params = LayoutParams(tabWidth, defaultLayoutHeight)
            params.gravity = Gravity.BOTTOM
            // 因为是等分，又是FrameLayout，所以直接设置tabView距离屏幕左边的距离
            params.leftMargin = index * tabWidth

            //创建每个tabView,赋值，添加到布局,并且保存
            val bottomTabView = BottomTabView(context).apply {
                setTabInfo(bottomTabBean)
                setOnClickListener {
                    onSelect(bottomTabBean)
                }
            }

            if (enterPosition == index) {

            }

            flBottomLayout.addView(bottomTabView, params)
            bottomTabViewList[bottomTabBean] = bottomTabView
        }
        addLayoutLineWidth()
        addLayoutBackgroud()

        val flPrams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        flPrams.gravity = Gravity.BOTTOM
        addView(flBottomLayout, flPrams)


    }


    /**
     * 每个tabView的点击方法
     */
    private fun onSelect(nextTabBeanInfo: BottomTabBean) {
        val nextTabView = bottomTabViewList[nextTabBeanInfo]!!
        val prevTabView = bottomTabViewList[prevTabBeanInfo]!!

        nextTabView.tabSelect(nextTabBeanInfo, prevTabBeanInfo!!, prevTabView)
        prevTabBeanInfo = nextTabBeanInfo
    }


    /**
     *  添加tablayout和主页面之间的分割线
     */
    private fun addLayoutLineWidth() {

    }

    /**
     * 添加tablayout的背景
     */
    private fun addLayoutBackgroud() {

    }

    /**
     * 得到进入时选中那一个的bean
     */
    private fun setPrevTabBeanInfo() {
        if (enterPosition < 0 || enterPosition > bottomTabList.size) {
            prevTabBeanInfo = bottomTabList[0]
            return
        }

        prevTabBeanInfo = bottomTabList[enterPosition]

    }

}