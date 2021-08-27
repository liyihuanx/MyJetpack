package liyihuan.app.android.module_ui.tab.bottom

import liyihuan.app.android.module_ui.R

/**
 * @author created by liyihuanx
 * @date 2021/8/27
 * @description: 类的描述
 */
interface IBottomViewController {
    fun defaultNormalColor(): Int

    fun defaultSelectColor(): Int

    open fun defaultLayoutBean(layoutBean: LayoutBean)

//    fun defaultLineWidth() = 1f
//
//    fun defaultLineColor() = "#dfe0e1"
//
//    fun defaultLayoutHeight() = 50
//
//    fun defaultLayoutAlpha() = 1f
//
//    fun defaultLayoutBackground() = "#ffffff"


    // 构建默认的
    companion object {

        var defaultLineWidth: Float? = 1f
        var defaultLineColor: String? = "#dfe0e1"
        var defaultLayoutHeight: Int? = 50
        var defaultLayoutAlpha: Float? = 1f
        var layoutBackground: String? = "#ffffff"

        var DEFAULT = object : IBottomViewController {
            override fun defaultNormalColor(): Int {
                return R.color.defaultNormalColor
            }

            override fun defaultSelectColor(): Int {
                return R.color.defaultSelectColor
            }

            override fun defaultLayoutBean(layoutBean: LayoutBean) {
                layoutBean.let {
                    defaultLineWidth = layoutBean.defaultLineWidth ?: 1f
                    defaultLineColor = layoutBean.defaultLineColor ?: "#dfe0e1"
                    defaultLayoutHeight = layoutBean.defaultLayoutHeight ?: 50
                    defaultLayoutAlpha = layoutBean.defaultLayoutAlpha ?: 1f
                    layoutBackground = layoutBean.layoutBackground ?: "#ffffff"
                }
            }
        }
    }
}