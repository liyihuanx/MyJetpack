package com.liyihuanx.myjetpack

import android.util.Log
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.liyihuan.module_common.RouterPath
import com.liyihuan.module_common.utils.LiveDataBus
import com.liyihuan.module_common.utils.lazyVm
import com.liyihuanx.module_base.activity.BaseActivity
import com.liyihuanx.myjetpack.databinding.ActivitySecondBinding
import kotlinx.android.synthetic.main.activity_second.*

@Route(path = RouterPath.Main.secondActivity)
class SecondActivity : BaseActivity<ActivitySecondBinding>() {

    private val twoVm by lazyVm<TwoViewModel>()

    override fun getLayout(): Int {
        return R.layout.activity_second
    }

    override fun bindLayoutData() {
        btnLiveData.setOnClickListener {
            LiveDataBus.with<String>("TestLiveDataBus")
                .observerSticky(this, false) {
                    Log.d("QWER", "onCreate: $it")
                }
        }

        btnHttp.setOnClickListener {
            twoVm.http()
        }
        btnFinish.setOnClickListener { finish() }
    }

    override fun observeLiveData() {
        twoVm.getHttpData.observe(this, Observer {
            Log.d("QWER", "http: ${Gson().toJson(it)}")
        })
    }

}