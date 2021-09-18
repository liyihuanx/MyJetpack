package com.liyihuanx.module_mine

import com.liyihuanx.module_base.fragment.BaseFragment
import com.liyihuanx.module_base.fragment.BaseLazyFragment
import com.liyihuanx.module_base.fragment.MainFragment
import com.liyihuanx.module_mine.databinding.FragmentMineBinding

/**
 * @ClassName: MineFragment
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/9/14 22:47
 */
class MineFragment : MainFragment<FragmentMineBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initViewOrData() {

    }

    override fun observeLiveData() {

    }

    override val getTagName: String
        get() = "MineFragment"
}