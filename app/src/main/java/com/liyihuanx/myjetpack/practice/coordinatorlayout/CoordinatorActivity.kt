package com.liyihuanx.myjetpack.practice.coordinatorlayout

import android.annotation.SuppressLint
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.liyihuanx.module_base.activity.NormalActivity
import com.liyihuanx.myjetpack.R
import com.liyihuanx.myjetpack.databinding.ActivityCoordinatorBinding
import kotlinx.android.synthetic.main.activity_coordinator.*

/**
 * @author created by liyihuanx
 * @date 2021/9/26
 * @description: 类的描述
 */
class CoordinatorActivity : NormalActivity<ActivityCoordinatorBinding>() {


    override fun isNeedToolBar(): Boolean {
        return false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun bindViewOrData() {
        coordinatorToolbar.logo = resources.getDrawable(R.drawable.tab_home_s)
        coordinatorToolbar.title = "这是标题"
        coordinatorToolbar.inflateMenu(R.menu.menu)

        val arrayListOf = ArrayList<String>()
        repeat(100) {
            arrayListOf.add("$it")
        }
        val coordinatorAdapter = CoAdapter()
        coordinatorAdapter.setNewInstance(arrayListOf)
        coordinatorRecycler.layoutManager = LinearLayoutManager(this)
        coordinatorRecycler.adapter = coordinatorAdapter
    }

    override fun getLayout(): Int {
        return R.layout.activity_coordinator
    }
}
