package com.liyihuanx.myjetpack.practice.coordinatorlayout

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.liyihuanx.module_base.activity.BaseActivity
import com.liyihuanx.module_common.RouterPath
import com.liyihuanx.myjetpack.R
import kotlinx.android.synthetic.main.activity_collapsing.*

/**
 * @author created by liyihuanx
 * @date 2021/9/26
 * @description: 类的描述
 */
@Route(path = RouterPath.Main.CollapsingActivity)
class CollapsingActivity : BaseActivity() {
    override fun isNeedToolBar(): Boolean {
        return false
    }

    override fun bindViewOrData() {

        val arrayListOf = ArrayList<String>()
        repeat(100) {
            arrayListOf.add("$it")
        }
        val coordinatorAdapter = CoAdapter()
        coordinatorAdapter.setNewInstance(arrayListOf)
        collapsingRv.layoutManager = LinearLayoutManager(this)
        collapsingRv.adapter = coordinatorAdapter
    }

    override fun getLayout(): Int {
        return R.layout.activity_collapsing
    }
}