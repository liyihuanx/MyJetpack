package com.liyihuan.module_two

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.liyihuan.module_common.bean.ChapterBean
import com.liyihuanx.module_base.utils.asToast
import com.liyihuanx.module_base.utils.lazyVm
import com.liyihuan.module_common.viewmodel.TestViewModel
import com.liyihuan.module_two.databinding.FragmentTwoBinding
import com.liyihuanx.module_base.fragment.LazyRecyclerFragment
import com.liyihuanx.module_base.fragment.TimeUnit
import com.liyihuanx.module_base.refresh.SmartRecyclerView
import kotlinx.android.synthetic.main.fragment_two.*

/**
 * @ClassName: TowFragment
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/9/14 22:54
 */
class TwoFragment : LazyRecyclerFragment<ChapterBean,FragmentTwoBinding>(){

    private val twoVm by lazyVm<TestViewModel>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_two
    }

    override fun observeLiveData() {
        twoVm.getHttpData.observe(this, {
            mSmartRecycler.onFetchDataFinish(it)
        })
    }

    private fun loadData() {
        twoVm.http()
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
        get() = TwoAdapter().also {
            it.setOnItemClickListener { adapter, view, position ->
                "$position".asToast()
            }
        }

    override val layoutManager: RecyclerView.LayoutManager
        get() = LinearLayoutManager(context)

    override val fetcherFuc: (page: Int) -> Unit
        get() = {
            loadData()
        }
}