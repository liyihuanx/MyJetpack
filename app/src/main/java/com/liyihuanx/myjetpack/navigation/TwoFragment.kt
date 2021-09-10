package com.liyihuanx.myjetpack.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.liyihuanx.module_base.fragment.BaseFragment
import com.liyihuanx.module_base.http.RepositoryManager
import com.liyihuanx.module_base.http.request.HttpConverterFactory
import com.liyihuanx.module_base.utils.AppContext
import com.liyihuanx.myjetpack.ChapterBean
import com.liyihuanx.myjetpack.ConfigService
import com.liyihuanx.myjetpack.R
import com.liyihuanx.myjetpack.TwoViewModel
import com.liyihuanx.myjetpack.databinding.FragmentTwoBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_two.*
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
    override fun getLayoutId(): Int {
        return R.layout.fragment_two
    }

    override fun initViewOrData() {
        val create = ViewModelProvider.AndroidViewModelFactory(AppContext.get())
            .create(TwoViewModel::class.java)
        tvHttp.setOnClickListener {
            create.http()
        }

        create.getHttpData.observe(this, Observer {
            Log.d("QWER", "initViewOrData: ${Gson().toJson(it)}")
        })
    }
}