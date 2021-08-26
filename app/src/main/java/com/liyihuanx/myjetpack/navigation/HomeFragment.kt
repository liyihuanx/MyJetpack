package com.liyihuanx.myjetpack.navigation

import android.util.Log
import android.view.View
import com.liyihuanx.module_base.fragment.BaseFragment
import com.liyihuanx.myjetpack.R
import com.liyihuanx.myjetpack.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @author created by liyihuanx
 * @date 2021/8/23
 * @description: 类的描述
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }


    /**
     * 方法引用，但是参数固定写死了
     */
    fun onReferencesClick(v: View) {
        Log.d("QWER", "onReferencesClick: ${v.id}")
    }

    /**
     * 自定义的点击 用lambda去引用
     * 可以随意加参数
     */
    fun onClick(){

    }


    override fun initViewOrData() {
        mBinding.homeHelper = this
    }
}