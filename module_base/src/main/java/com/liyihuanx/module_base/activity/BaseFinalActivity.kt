package com.liyihuanx.module_base.activity

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import com.liyihuanx.module_base.R

/**
 * @author created by liyihuanx
 * @date 2021/9/18
 * @description: 类的描述
 */
abstract class BaseFinalActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {

    /**
     * 根布局
     */
    private lateinit var mRootView: ViewGroup

    private var mContentView: View? = null

    private lateinit var mInflater: LayoutInflater

    private var mToolbar: Toolbar? = null

    private var centerTitle: TextView? = null



    abstract fun getLayout(): Int

    abstract fun initViewForBase()

    abstract fun bindViewOrData()
    open fun observeLiveData() {}
    open fun setViewStatusBar(){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mInflater = getLayInflater()
        mContentView = createContainerView()
        setContentView(mContentView)
        mRootView = findViewById(android.R.id.content)
        if (isNeedToolBar()) {
            val toolbarLayoutId = if (isTitleCenter()) {
                R.layout.view_center_toolbar
            } else {
                R.layout.view_normal_toolbar
            }
            mToolbar = mInflater.inflate(toolbarLayoutId, null) as Toolbar
            var barHeight = resources.getDimensionPixelOffset(R.dimen.abc_action_bar_default_height_material)
            mRootView.addView(
                mToolbar, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, barHeight)
            )

            isTitleCenter().takeIf { true }.also { centerTitle = mToolbar?.findViewById(R.id.tvToolBarTitle) }

            if (requestToolbarMode() == Layer) {
                val params = mContentView!!.layoutParams as FrameLayout.LayoutParams
                params.let {
                    it.setMargins(
                        params.leftMargin,
                        barHeight,
                        params.rightMargin,
                        params.bottomMargin
                    )
                    mContentView!!.layoutParams = it
                }
            }

            setToolbarTitle(getToolBarTitle(), getToolBarTitleColor(), getToolBarTitleSize())
            setupToolbar()
        }

        initViewForBase()
    }


    private fun setupToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar)
            //显示返回键
            supportActionBar?.setDisplayHomeAsUpEnabled(homeAsUpEnable())
            //可响应返回键点击事件
            supportActionBar?.setDisplayShowHomeEnabled(true)
            //返回键
            requestNavigationIcon().let {
                if (it > 0) {
                    supportActionBar?.setHomeAsUpIndicator(it)
                }
            }

            //是否展示默认标题
            if (isTitleCenter()) {
                supportActionBar?.setDisplayShowTitleEnabled(false)
            }

            val toolbarBg = requestToolBarBackground()
            mToolbar?.background = toolbarBg
            mToolbar?.setOnTouchListener(toolbarTouch)
            mToolbar?.setOnMenuItemClickListener(this)
            mToolbar?.setNavigationOnClickListener {
                // TODO 加连击判断
                onBackPressed()
            }
        }
    }


    private fun setToolbarTitle(title: CharSequence, color: Int, size: Float) {
        if (mToolbar != null) {
            if (isTitleCenter()) {
                centerTitle?.text = title
                centerTitle?.setTextColor(resources.getColor(color))
                centerTitle?.textSize = size
            } else {
                if (mToolbar != null) {
                    mToolbar?.title = title
                }
            }
        }
    }


    private fun getLayInflater(): LayoutInflater {
        return LayoutInflater.from(this)
    }


    protected open fun createContainerView(): View {
        return LayoutInflater.from(this).inflate(getLayout(), null)
    }


    /**************************** 下面这些配置以后可以做成构建者模式，可能会比较方便使用 ********************************************/


    /**
     * 是否需要标题栏
     */
    open fun isNeedToolBar(): Boolean {
        return true
    }


    /**
     * toolbar 返回键
     */
    @DrawableRes
    open fun requestNavigationIcon(): Int {
        return R.drawable.btn_black_back
    }

    /**
     * 是否显示返回键。默认显示
     */
    open fun homeAsUpEnable(): Boolean {
        return true
    }


    /**
     * 设置toolbar 背景样式
     */
    open fun requestToolBarBackground(): Drawable? {
        return  getDrawable(R.color.color_white)
    }

    /**
     * 标题栏文字内容
     */
    open fun getToolBarTitle(): String {
        return title as String
    }

    /**
     * 标题栏文字大小
     */
    open fun getToolBarTitleSize(): Float {
        return 16f
    }

    /**
     * 标题栏文字颜色
     */
    open fun getToolBarTitleColor(): Int {
        return R.color.color_black
    }


    /**
     * 标题栏模式
     */
    open fun requestToolbarMode(): Int {
        return Layer
    }

    /**
     * 标题栏文字居中
     */
    open fun isTitleCenter(): Boolean {
        return true
    }


    companion object {
        /**
         * toolbar与content为平行的上下层关系
         */
        const val Parallel = 0


        /**
         * toolbar与content同一层
         */
        const val Layer = 1

    }

    /**
     * toolbar的菜单栏目
     */
    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> {
            }
        }
        return false
    }


    /**
     * 监听toolbar的touch事件
     */
    @SuppressLint("ClickableViewAccessibility")
    open val toolbarTouch = View.OnTouchListener { v, event ->
        if (event.action == MotionEvent.ACTION_DOWN) {
            // TODO 做键盘隐藏
        }
        false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // 按音量下键
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            try {
                val clazz = Class.forName("com.liyihuanx.module_debugtool.DebugToolDialog")
                val debugToolDialog = clazz.getConstructor().newInstance() as DialogFragment
                debugToolDialog.show(supportFragmentManager, "debug_tool")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}