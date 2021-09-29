package com.liyihuanx.module_two

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.liyihuanx.module_common.bean.ChapterBean
import com.liyihuanx.module_base.utils.asToast
import com.liyihuanx.module_base.utils.lazyVm
import com.liyihuanx.module_common.viewmodel.TestViewModel
import com.liyihuanx.module_two.databinding.FragmentTwoBinding
import com.liyihuanx.annotation.NetStrategy
import com.liyihuanx.module_base.fragment.MainLazyRecyclerFragment
import com.liyihuanx.module_base.fragment.TimeUnit
import com.liyihuanx.module_base.refresh.SmartRecyclerView
import com.liyihuanx.module_logutil.logpackage.MLog
import kotlinx.android.synthetic.main.fragment_two.*

/**
 * @ClassName: TowFragment
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/9/14 22:54
 */
class TwoFragment : MainLazyRecyclerFragment<ChapterBean, FragmentTwoBinding>(){

    private val twoVm by lazyVm<TestViewModel>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_two
    }

    override fun observeLiveData() {
        twoVm.getHttpData.observe(this, {
            MLog.v(it)
            mSmartRecycler.onFetchDataFinish(it)
        })
    }


    override val refreshTime: Float
        get() = 10f

    override val timeUnit: Long
        get() = TimeUnit.SECOND


    override val getTagName: String
        get() = "TwoFragment"

    override val mSmartRecycler: SmartRecyclerView
        get() = mTwoSmartRecycler

    override val adapter: BaseQuickAdapter<ChapterBean, *>
        get() = TwoDataBindingAdapter().also {
            it.setOnItemClickListener { adapter, view, position ->
                "$position".asToast()
            }
        }

    override val layoutManager: RecyclerView.LayoutManager
        get() = LinearLayoutManager(context)

    override val fetcherFuc: (page: Int) -> Unit
        get() = {
            loadData(it)
        }

    private fun loadData(page: Int) {
        if (page == 0) {
            twoVm.http(NetStrategy.CACHE_ONLY, page)
        } else {
            // 加载更多不做缓存
            twoVm.http(NetStrategy.CACHE_ONLY, page)
        }
    }
}