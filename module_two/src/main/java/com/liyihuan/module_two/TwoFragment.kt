package com.liyihuan.module_two

import com.liyihuan.module_two.databinding.FragmentTwoBinding
import com.liyihuanx.module_base.fragment.BaseFragment

/**
 * @ClassName: TowFragment
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/9/14 22:54
 */
class TwoFragment : BaseFragment<FragmentTwoBinding>(){
    override fun getLayoutId(): Int {
        return R.layout.fragment_two
    }

    override fun initViewOrData() {

    }

}