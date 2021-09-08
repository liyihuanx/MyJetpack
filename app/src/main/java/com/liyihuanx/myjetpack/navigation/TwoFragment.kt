package com.liyihuanx.myjetpack.navigation

import androidx.lifecycle.ViewModelProvider
import com.liyihuanx.module_base.fragment.BaseFragment
import com.liyihuanx.module_base.http.RepositoryManager
import com.liyihuanx.module_base.utils.AppContext
import com.liyihuanx.myjetpack.ConfigService
import com.liyihuanx.myjetpack.R
import com.liyihuanx.myjetpack.TwoViewModel
import com.liyihuanx.myjetpack.databinding.FragmentTwoBinding
import kotlinx.android.synthetic.main.fragment_two.*

/**
 * @author created by liyihuanx
 * @date 2021/8/23
 * @description: 类的描述
 */
class TwoFragment : BaseFragment<FragmentTwoBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_two
    }

    override fun initViewOrData() {

        tvHttp.setOnClickListener {
            val create = ViewModelProvider.AndroidViewModelFactory(AppContext.get())
                .create(TwoViewModel::class.java)

            create.http()
        }

    }
}