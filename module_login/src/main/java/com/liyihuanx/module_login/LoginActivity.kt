package com.liyihuanx.module_login

import com.liyihuanx.module_base.activity.BaseDataBindingActivity
import com.liyihuanx.module_login.databinding.ActivityLoginBinding

/**
 * @author created by liyihuanx
 * @date 2021/9/15
 * @description: 类的描述
 */
class LoginActivity : BaseDataBindingActivity<ActivityLoginBinding>() {
    override fun getLayout(): Int {
        return R.layout.activity_login
    }

    override fun bindViewOrData() {
    }
}