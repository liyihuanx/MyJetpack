package com.liyihuan.module_three

import com.liyihuan.module_three.databinding.FragmentThreeBinding
import com.liyihuanx.module_base.fragment.BaseFragment
import com.liyihuanx.module_base.fragment.BaseLazyFragment

/**
 * @ClassName: ThreeFragment
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/9/14 23:00
 */
class ThreeFragment : BaseLazyFragment<FragmentThreeBinding>(){
    override fun getLayoutId(): Int {
        return R.layout.fragment_three
    }

    override fun initViewOrData() {

    }

    override fun observeLiveData() {

    }

    override val getTagName: String
        get() = "ThreeFragment"
}