package com.liyihuanx.module_base.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @author created by liyihuanx
 * @date 2021/8/23
 * @description: 类的描述
 */
abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {


    lateinit var mBinding: T


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayout())
        bindLayoutData()
    }


    abstract fun getLayout(): Int
    abstract fun bindLayoutData()
}