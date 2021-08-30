package liyihuan.app.android.module_ui.tab.bottom

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import liyihuan.app.android.module_ui.R
import liyihuan.app.android.module_ui.databinding.LayoutTestBindingImpl

/**
 * @author created by liyihuanx
 * @date 2021/8/30
 * @description: 类的描述
 */
class TestLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutTestBindingImpl by lazy {
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_test, this, true)
    }

    init {
        binding.testName = "测试"
    }


    companion object {
        @JvmStatic
        @BindingAdapter(value = ["tes1"], requireAll = false)
        fun test(tablayout: TestLayout, tes1: String) {

        }
    }
}