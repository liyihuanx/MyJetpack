package com.liyihuanx.myjetpack.navigation

import com.liyihuanx.module_base.fragment.BaseFragment
import com.liyihuanx.myjetpack.R
import com.liyihuanx.myjetpack.databinding.FragmentTwoBinding

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
    }
}