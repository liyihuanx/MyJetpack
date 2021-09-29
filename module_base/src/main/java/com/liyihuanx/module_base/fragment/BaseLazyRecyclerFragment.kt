package com.liyihuanx.module_base.fragment

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.liyihuanx.module_base.refresh.CommonEmptyView
import com.liyihuanx.module_base.refresh.SmartRecyclerView
import com.scwang.smartrefresh.layout.api.RefreshFooter
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.layout_refresh_recyclerview.view.*
import liyihuan.app.android.lazyfragment.refresh.IEmptyView


/**
 * @ClassName: LazyRecyclerView
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/6/8 21:16
 */
abstract class BaseLazyRecyclerFragment<T, DB : ViewDataBinding> : BaseLazyFragment<DB>() {

    private val commonEmptyView by lazy { CommonEmptyView(requireContext()) }

    private val classicsHeader by lazy { ClassicsHeader(context) }

    private val classicsFooter by lazy { ClassicsFooter(context) }

    /**
     * 通用emptyView
     */
    open fun getEmptyView(): IEmptyView? {
        return commonEmptyView
    }

    open fun setRefreshHeader(): RefreshHeader {
        return classicsHeader
    }

    open fun setRefreshFooter(): RefreshFooter {
        return classicsFooter
    }

    abstract val mSmartRecycler: SmartRecyclerView

    abstract val adapter: BaseQuickAdapter<T, *>

    abstract val layoutManager: RecyclerView.LayoutManager

    /**
     * 刷新回调
     */
    abstract val fetcherFuc: ((page: Int) -> Unit)



    override fun initViewOrData() {
        // 把RecyclerView 和 SmartRefreshHelper 建立联系
        mSmartRecycler.recyclerView.layoutManager = layoutManager
        mSmartRecycler.setRefreshHeader(setRefreshHeader())
        mSmartRecycler.setRefreshFooter(setRefreshFooter())

        mSmartRecycler.setUp(
            adapter,
            getEmptyView(),
            loadMoreNeed(),
            refreshNeed(),
            fetcherFuc
        )
    }




    override fun onFragmentResume() {
        super.onFragmentResume()
        mSmartRecycler.startRefresh()
    }

    /**
     * 离开停止刷新
     */
    override fun onFragmentPause() {
        super.onFragmentResume()
        mSmartRecycler.finishRefresh()
    }


    open fun loadMoreNeed(): Boolean {
        return true
    }

    open fun refreshNeed(): Boolean {
        return true
    }

    /**
     * 一进入是否需要刷新
     */
    open fun isRefreshAtOnStart(): Boolean {
        return true
    }


}