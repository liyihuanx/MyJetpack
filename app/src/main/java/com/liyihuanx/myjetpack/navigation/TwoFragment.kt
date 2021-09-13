package com.liyihuanx.myjetpack.navigation

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.liyihuanx.module_base.fragment.BaseFragment
import com.liyihuanx.module_base.http.RepositoryManager
import com.liyihuanx.module_base.http.request.HttpConverterFactory
import com.liyihuanx.module_base.utils.AppContext
import com.liyihuanx.myjetpack.*
import com.liyihuanx.myjetpack.databinding.FragmentTwoBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_two.*
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author created by liyihuanx
 * @date 2021/8/23
 * @description: 类的描述
 */
class TwoFragment : BaseFragment<FragmentTwoBinding>() {

    val create by lazy {
        ViewModelProvider(this)[TwoViewModel::class.java]
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_two
    }

    override fun initViewOrData() {
        tvHttp.setOnClickListener {
            val intent = Intent(context, SecondActivity::class.java)
            startActivity(intent)
        }

        create.getHttpData.observe(this, Observer {
            Log.d("QWER", "initViewOrData: ${Gson().toJson(it)}")
        })

        lifecycleScope.launch {

        }
    }
}