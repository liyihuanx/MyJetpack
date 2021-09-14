package com.liyihuanx.module_ui.tab.bottom

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.liyihuanx.module_base.utils.DisplayUtil
import com.liyihuanx.module_ui.R

/**
 * @author created by liyihuanx
 * @date 2021/8/27
 * @description: 加载每一个bottomView
 */
class BottomTabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr),
    IBottomLayout {
    private val TAG_TAB_BOTTOM_LAYOUT = "TAG_TAB_BOTTOM_LAYOUT"

    private val tabSelectedChangeListeners = ArrayList<IBottomLayout.OnTabSelectedListener>()

    private var tabSelectedInterceptorListeners: IBottomLayout.OnTabSelectInterceptorListener? = null

    // 所有的tab的list
    private var bottomTabList = ArrayList<BottomTabBean>()

    private val bottomAlpha = 1f

    //TabBottom高度
    private val defaultLayoutHeight = 50f

    //TabBottom的头部线条高度
    private val bottomLineHeight = 0.5f

    //TabBottom的头部线条颜色
    private var splitLineColor = -1

    private var defaultSelectColor = 0
    private var defaultNormalColor = 0


    private var selectedInfo: BottomTabBean? = null

    private var startPosition: Int = 0

    init {
        initCustomAttrs(context, attrs)
    }

    private fun initCustomAttrs(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BottomTabLayout)
        defaultSelectColor = typedArray.getColor(
            R.styleable.BottomTabLayout_defaultSelectColor,
            Color.parseColor("#d44949")
        )
        defaultNormalColor = typedArray.getColor(
            R.styleable.BottomTabLayout_defaultNormalColor,
            Color.parseColor("#333333")
        )

        splitLineColor = typedArray.getColor(
            R.styleable.BottomTabLayout_splitLineColor,
            -1
        )

        typedArray.recycle()
    }


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
        // 清除接口
        tabSelectedChangeListeners.clear()

        // 父布局
        val flBottomLayout = FrameLayout(context)
        flBottomLayout.tag = TAG_TAB_BOTTOM_LAYOUT

        val defaultLayoutHeight = DisplayUtil.dp2px(defaultLayoutHeight, resources)
        val tabWidth = DisplayUtil.getDisplayWidthInPx(context) / bottomTabList.size


        bottomTabList.forEachIndexed { index, bottomTabBean ->
            if (bottomTabBean.start == true) {
                selectedInfo = bottomTabBean
            }

            //用LinearLayout当动态改变child大小后Gravity.BOTTOM会失效
            val params = LayoutParams(tabWidth, defaultLayoutHeight)
            params.gravity = Gravity.BOTTOM
            // 因为是等分，又是FrameLayout，所以直接设置tabView距离屏幕左边的距离
            params.leftMargin = index * tabWidth

            //创建每个tabView,赋值，添加到布局,并且保存
            val bottomTabView = BottomTabView(
                context
            ).apply {
                setTabInfo(bottomTabBean, defaultNormalColor, defaultSelectColor)
                setOnClickListener {
                    onSelected(bottomTabBean)
                }

            }
            tabSelectedChangeListeners.add(bottomTabView)
            flBottomLayout.addView(bottomTabView, params)
        }
        addLayoutLineWidth()
        addLayoutBackground()

        val flPrams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        flPrams.gravity = Gravity.BOTTOM
        addView(flBottomLayout, flPrams)

        // 默认选中第一个
//        setCurrentSelect(startPosition)
    }

    override fun addTabSelectInterceptorListener(listener: IBottomLayout.OnTabSelectInterceptorListener) {
        tabSelectedInterceptorListeners = listener
    }


    /**
     *  添加tablayout和主页面之间的分割线
     */
    private fun addLayoutLineWidth() {
        if (splitLineColor != -1) {
            val bottomLine = View(context)
            bottomLine.setBackgroundColor(splitLineColor)
            val bottomLineParams =
                LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    DisplayUtil.dp2px(bottomLineHeight)
                )
            bottomLineParams.gravity = Gravity.BOTTOM
            bottomLineParams.bottomMargin =
                DisplayUtil.dp2px(defaultLayoutHeight - bottomLineHeight)
            addView(bottomLine, bottomLineParams)
            bottomLine.alpha = bottomAlpha
        }
    }

    /**
     * 添加tablayout的背景
     */
    private fun addLayoutBackground() {

    }


    fun setCurrentSelect(position: Int) {
        if (position > bottomTabList.size) {
            return
        }
        startPosition = position
        onSelected(bottomTabList[position])
    }

    /**
     * 每个tabView的点击方法
     */
    private fun onSelected(nextInfo: BottomTabBean) {
        val indexOf = bottomTabList.indexOf(nextInfo)
        // 点击前拦截
        val onTabSelectedInterceptor = tabSelectedInterceptorListeners?.selectBeforeInterceptor(
            indexOf,
            selectedInfo,
            nextInfo
        )

        if (onTabSelectedInterceptor == true) {
            return
        }

        tabSelectedChangeListeners.forEach {
            it.onTabSelectedChange(indexOf, selectedInfo, nextInfo)
        }
        this.selectedInfo = nextInfo

        // 点击后拦截
        tabSelectedInterceptorListeners?.selectAfterInterceptor(indexOf)

//        总是忘记，记录一下
//        run this 返回结果
//        apply this 返回调用的人
//        let it 返回结果
//        also it 返回调用的人
    }

}