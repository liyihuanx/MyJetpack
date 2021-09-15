package com.liyihuanx.myjetpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.liyihuan.module_common.RouterPath
import com.liyihuan.module_common.utils.LiveDataBus
import kotlinx.android.synthetic.main.activity_second.*

@Route(path = RouterPath.Main.secondActivity)
class SecondActivity : AppCompatActivity() {
    private val create by lazy {
        ViewModelProvider(this)[TwoViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)


        btnLiveData.setOnClickListener {
            LiveDataBus.with<String>("TestLiveDataBus")
                .observerSticky(this, false) {
                    Log.d("QWER", "onCreate: $it")
                }
        }

        btnHttp.setOnClickListener {
            create.http()
        }
        btnFinish.setOnClickListener { finish() }

    }
}