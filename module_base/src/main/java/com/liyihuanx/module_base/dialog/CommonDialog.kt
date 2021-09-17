package com.liyihuanx.module_base.dialog

import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.liyihuanx.module_base.R
import kotlinx.android.synthetic.main.dialog_common.*
import java.lang.reflect.Field

/**
 * @author created by liyihuanx
 * @date 2021/9/17
 * @description: 类的描述
 */
class CommonDialog : BaseDialogFragment() {
    override fun getViewLayoutId(): Int {
        return R.layout.dialog_common
    }

    init {
        applyCancelable(true)
        applyGravityStyle(Gravity.CENTER)
    }

    companion object {
        fun newInstance(
            title: String,
            content: String,
            isNeedCancelBtn: Boolean,
            positiveText: String,
            negativeText: String
        ): CommonDialog {
            val b = Bundle()
            b.putString("title", title)
            b.putString("content", content)
            b.putBoolean("isNeedCancelBtn", isNeedCancelBtn)
            b.putString("positiveText", positiveText)
            b.putString("negativeText", negativeText)
            val f = CommonDialog()
            f.arguments = b
            return f
        }
    }



    override fun initView() {
        arguments?.apply {
            // Title
            val title = getString("title")
            if (TextUtils.isEmpty(title)) {
                tvTitle.visibility = View.GONE
            } else {
                tvTitle.text = title
            }

            // 内容
            val content = getString("content")
            tvContent.text = Html.fromHtml(content)

            // 取消按钮
            val isNeedCancelBtn = getBoolean("isNeedCancelBtn", true)
            if (!isNeedCancelBtn) {
                vV.visibility = View.GONE
                btnCancel.visibility = View.GONE
            } else {
                val negativeText = getString("negativeText")
                if (!TextUtils.isEmpty(negativeText)) {
                    btnCancel.text = negativeText
                }
            }

            // 确定按钮
            val positiveText = getString("positiveText")
            if (!TextUtils.isEmpty(positiveText)) {
                btnConfirm.text = positiveText
            }
        }
        btnCancel.setOnClickListener {
            mDefaultListener?.onDialogNegativeClick(this, Any())
            dismiss()
        }

        btnConfirm.setOnClickListener {
            dismiss()
            mDefaultListener?.onDialogPositiveClick(this, Any())
        }

    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val mDismissed: Field = this.javaClass.superclass!!.getDeclaredField("mDismissed")
            val mShownByMe: Field = this.javaClass.superclass!!.getDeclaredField("mShownByMe")
            mDismissed.isAccessible = true
            mShownByMe.isAccessible = true
            mDismissed.setBoolean(this, false)
            mShownByMe.setBoolean(this, true)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        val ft: FragmentTransaction = manager.beginTransaction()
        ft.add(this, javaClass.simpleName)
        ft.commitAllowingStateLoss()
    }
}

class CommonDialogBuild {
    private var tittle = ""
    private var content = ""
    private var positiveText = ""
    private var negativeText = ""
    private var isNeedCancelBtn = true
    private var dialogListener: BaseDialogFragment.BaseDialogListener? = null

    fun setTittle(tittle: String): CommonDialogBuild {
        this.tittle = tittle
        return this
    }

    fun setContent(content: String): CommonDialogBuild {
        this.content = content
        return this
    }

    fun setPositiveText(confirm: String): CommonDialogBuild {
        positiveText = confirm
        return this
    }

    fun setNegativeText(cancel: String): CommonDialogBuild {
        negativeText = cancel
        return this
    }


    fun isNeedCancelBtn(isNeedCancelBtn: Boolean): CommonDialogBuild {

        this.isNeedCancelBtn = isNeedCancelBtn
        return this
    }

    fun setListener(listener: BaseDialogFragment.BaseDialogListener): CommonDialogBuild {
        dialogListener = listener
        return this
    }

    fun build(): BaseDialogFragment {
        val d = CommonDialog.newInstance(tittle, content, isNeedCancelBtn, positiveText,negativeText)
        dialogListener?.apply { d.setDefaultListener(this) }

        return d
    }
}