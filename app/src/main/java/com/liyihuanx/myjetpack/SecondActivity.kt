package com.liyihuanx.myjetpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.liyihuanx.module_base.http.RepositoryManager
import com.liyihuanx.module_base.utils.AppContext
import com.liyihuanx.module_base.utils.lifecycleScopeCoroutine
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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