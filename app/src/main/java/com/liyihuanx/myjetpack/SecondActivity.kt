package com.liyihuanx.myjetpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.liyihuan.module_common.RouterPath
import kotlinx.android.synthetic.main.activity_second.*

@Route(path = RouterPath.Main.secondActivity)
class SecondActivity : AppCompatActivity() {
    private val create by lazy {
        ViewModelProvider(this)[TwoViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)


        btnHttp.setOnClickListener {
            create.http()
        }
        btnFinish.setOnClickListener { finish() }

    }
}