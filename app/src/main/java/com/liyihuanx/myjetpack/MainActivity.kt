package com.liyihuanx.myjetpack

import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.liyihuanx.module_base.activity.BaseActivity
import com.liyihuanx.myjetpack.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun bindLayoutData() {
        mBinding.userInfo = UserInfoBean()

        mBinding.BnvLayout.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> Log.d("QWER", "${it.itemId}: ")
                R.id.menu_two -> Log.d("QWER", "${it.itemId}: ")
                R.id.menu_three -> Log.d("QWER", "${it.itemId}: ")
                R.id.menu_mine -> Log.d("QWER", "${it.itemId}: ")
            }
            true
        }


    }

}