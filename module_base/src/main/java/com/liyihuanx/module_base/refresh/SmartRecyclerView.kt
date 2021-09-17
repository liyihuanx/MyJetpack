package com.liyihuanx.module_base.refresh

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.liyihuanx.module_base.R
import com.scwang.smartrefresh.layout.api.RefreshFooter
import com.scwang.smartrefresh.layout.api.RefreshHeader
import kotlinx.android.synthetic.main.layout_refresh_recyclerview.view.*
import liyihuan.app.android.lazyfragment.refresh.IEmptyView

/**
 * @ClassName: SmartRecyclerView
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/6/8 21:49
 */
class SmartRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    lateinit var smartRefreshHelper: SmartRefreshHelper<*>

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_refresh_recyclerview, this, false)
        addView(view)
    }


    /**
     * 设置刷新头样式
     *
     * @param refreshHeader
     */
    fun setRefreshHeader(refreshHeader: RefreshHeader) {
        smartRefreshLayout.setRefreshHeader(refreshHeader)
    }

    fun setRefreshFooter(refreshFooter: RefreshFooter) {
        smartRefreshLayout.setRefreshFooter(refreshFooter)
    }

    /**
     * 手动调用触发刷新
     */
    fun startRefresh() {
        smartRefreshHelper.refresh()
    }

    /**
     * 手动停止刷新
     */
    fun finishRefresh() {
        smartRefreshHelper.finishRefresh()
    }

    /**
     * 将请求结果交给smartRefreshHelper处理
     */
    fun onFetchDataFinish(data: List<*>?, delay: Int? = null) {
        smartRefreshHelper.onFetchDataFinish(data as List<Nothing>?, delay)
    }



    /**
     * 初始化
     *
     * @param emptyView     空视图
     * @param adapter       适配器
     * @param fetcherFuc    刷新事件页回调　0开始
     */
    fun setUp(
        adapter: BaseQuickAdapter<*, *>,
        emptyView: IEmptyView?,
        loadMoreNeed: Boolean,
        isRefreshNeed: Boolean,
        fetcherFuc: (page: Int) -> Unit
    ) {
        if (emptyView != null) {
            flRecyContent.addView(emptyView.getContentView(), 0)
        }
        recyclerView.adapter = adapter
        smartRefreshHelper = SmartRefreshHelper(
            adapter,
            recyclerView,
            smartRefreshLayout,
            emptyView,
            loadMoreNeed,
            isRefreshNeed,
            fetcherFuc
        )
    }


}