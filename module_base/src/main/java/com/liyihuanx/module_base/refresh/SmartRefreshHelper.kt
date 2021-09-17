package com.liyihuanx.module_base.refresh

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.liyihuanx.module_base.utils.NetUtil
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import liyihuan.app.android.lazyfragment.refresh.IEmptyView
import liyihuan.app.android.lazyfragment.refresh.IEmptyView.Companion.HIDE_LAYOUT
import liyihuan.app.android.lazyfragment.refresh.IEmptyView.Companion.NETWORK_ERROR
import liyihuan.app.android.lazyfragment.refresh.IEmptyView.Companion.NODATA

/**
 * @ClassName: SmartRefreshHelper
 * @Description: 上拉下拉帮助类
 * @Author: liyihuan
 * @Date: 2021/6/8 21:27
 */
open class SmartRefreshHelper<T>(
    private val adapter: BaseQuickAdapter<T, *>,
    private val recycler_view: RecyclerView,
    private val refresh_layout: SmartRefreshLayout,
    private val emptyCustomView: IEmptyView?,

    private val isNeedLoadMore: Boolean = true,
    private val isNeedRefresh: Boolean = true,
    /**
     * 刷新回调
     */
    private val fetcherFuc: (page: Int) -> Unit
) {

    /**
     * 1.无网络，无缓存 --> 加载空视图
     * 2.无网络，有缓存 --> 加载缓存
     * 3.有网络，无缓存 --> 请求接口
     * 4.有网络，有缓存 --> 根据情况返回接口数据或是缓存
     */

    private var isLoadMoreing: Boolean = false
    private var isRefreshing: Boolean = false
    var currentPage = 0

    private var hasCache: Boolean = false

    private var cacheData = ArrayList<T>()

    init {
        if (isNeedRefresh) {
            refresh_layout.setOnRefreshListener {
                refresh()
            }
        }

        if (isNeedLoadMore) {
            refresh_layout.setOnLoadMoreListener {
                loadMore()
            }
        }
    }

    fun onFetchDataFinish(data: List<T>?, delay: Int? = null) {
        if (data != null && data.isNotEmpty()) {
            // 如果在加载中
            if (isLoadMoreing) {
                // 页数加一
                currentPage++
                adapter.addData(data)
                finishLoadMore(delay)

            } else if (isRefreshing) {
                adapter.setNewInstance(data as MutableList<T>?)
                finishRefresh(delay)
            }
            if (emptyCustomView?.getContentView()?.visibility == View.VISIBLE) {
                refreshEmptyView(NODATA)
            }
        } else {
            // 如果没有数据
            if (currentPage == 0) {
                finishRefresh(isSuccess = false)
                // 判断网络
                val connectedStatus = NetUtil.isNetworkAvailable(recycler_view.context)
                if (!connectedStatus) {
                    // 设置无网络
                    refreshEmptyView(NETWORK_ERROR)
                } else {
                    // 设置空状态
                    refreshEmptyView(NODATA)
                }
            } else {
                finishLoadMore(isSuccess = false)
            }
        }
        isLoadMoreing = false
        isRefreshing = false
    }

    /**
     * 刷新空视图状态
     */
    private fun refreshEmptyView(type: Int) {
        if (adapter.data.isEmpty() && recycler_view.childCount == 0) {
            emptyCustomView?.setErrorType(type)
        } else {
            emptyCustomView?.setErrorType(HIDE_LAYOUT)
        }
    }

    /**
     * 加载数据
     */
    private fun loadMore() {
        if (isRefreshing || isLoadMoreing) {
            return
        }
        isLoadMoreing = true
        currentPage += 1
        fetcherFuc(currentPage)
    }

    /**
     * 刷新数据
     */
    fun refresh() {
        // 如果在刷新 或者 在加载
        if (isRefreshing || isLoadMoreing) {
            return
        }

        // 没有跳出方法，这符合条件可以刷新
        isRefreshing = true
        // 当前页数重置
        currentPage = 0
        // 请求接口请求第一页
        fetcherFuc(0)

        refresh_layout.autoRefresh()
    }


    fun finishRefresh(delay: Int? = null, isSuccess: Boolean = true) {
        if (isRefreshing) {
            if (delay != null) {
                refresh_layout.finishRefresh(delay)
            } else {
                refresh_layout.finishRefresh(isSuccess)
            }
        }
    }

    fun finishLoadMore(delay: Int? = null, isSuccess: Boolean = true) {
        if (isLoadMoreing) {
            if (delay != null) {
                refresh_layout.finishLoadMore(delay)
            } else {
                refresh_layout.finishLoadMore(isSuccess)
            }
        }
    }

}