package com.liyihuanx.module_base.fragment

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.databinding.ViewDataBinding
import com.liyihuanx.module_base.R
import com.liyihuanx.module_base.utils.StatusBarUtil

/**
 * @author created by liyihuanx
 * @date 2021/9/18
 * @description: 类的描述
 */
abstract class MainFragment<T : ViewDataBinding> : BaseLazyFragment<T>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 添加一个空白view填充状态栏，这样只对LinearLayout有效
        addViewStatus()
    }


    /**
     * 添加状态栏占位视图
     */
    open val viewStatus by lazy {
        StatusBarUtil.addStatusViewWithColor(requireContext()).also {
            it.id = R.id.viewStatus
        }
    }


    private fun addViewStatus() {
        when (mBinding.root) {
            is RelativeLayout -> {
                val firstView = (mBinding.root as RelativeLayout)[0]
                val layoutParams = firstView.layoutParams as RelativeLayout.LayoutParams
                layoutParams.addRule(RelativeLayout.BELOW, viewStatus.id)
            }
            is ConstraintLayout -> {
                val firstView = (mBinding.root as ConstraintLayout)[0]
                val layoutParams = firstView.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.topToBottom = viewStatus.id
            }
            is FrameLayout -> {
                val firstView = (mBinding.root as FrameLayout)[0]
                val layoutParams = firstView.layoutParams as FrameLayout.LayoutParams
                layoutParams.setMargins(0,viewStatus.height,0,0)
            }

        }

        (mBinding.root as ViewGroup).addView(
            viewStatus,
            0,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                StatusBarUtil.getStatusBarHeight(requireContext())
            )
        )
    }


}