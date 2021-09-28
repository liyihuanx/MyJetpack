package com.liyihuanx.myjetpack.practice

import android.util.Log
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.liyihuanx.module_common.RouterPath
import com.liyihuanx.module_base.utils.LiveDataBus
import com.liyihuanx.module_base.utils.lazyVm
import com.liyihuanx.module_common.viewmodel.TestViewModel
import com.liyihuanx.module_base.activity.NormalActivity
import com.liyihuanx.myjetpack.R
import com.liyihuanx.myjetpack.databinding.ActivitySecondBinding
import kotlinx.android.synthetic.main.activity_second.*

@Route(path = RouterPath.Main.SecondActivity)
class SecondActivity : NormalActivity<ActivitySecondBinding>() {

    private val twoVm by lazyVm<TestViewModel>()

    override fun getLayout(): Int {
        return R.layout.activity_second
    }

    override fun bindViewOrData() {
        btnLiveData.setOnClickListener {
            LiveDataBus.with<String>("TestLiveDataBus")
                .observerSticky(this, false) {
                    Log.d("QWER", "onCreate: $it")
                }
        }

        btnHttp.setOnClickListener {
//            twoVm.http()
        }
        btnFinish.setOnClickListener { finish() }
    }

    override fun observeLiveData() {
        twoVm.getHttpData.observe(this, Observer {
            Log.d("QWER", "http: ${Gson().toJson(it)}")
        })
    }

}