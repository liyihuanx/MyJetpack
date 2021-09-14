package com.liyihuanx.module_ui.tab.bottom


/**
 * @ClassName: IBottomLayout
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/8/27 23:52
 */
interface IBottomLayout {

    fun bindBottomTabData(data: ArrayList<BottomTabBean>)

    interface OnTabSelectedListener {
        fun onTabSelectedChange(index: Int, prevInfo: BottomTabBean?, nextInfo: BottomTabBean)
    }


    fun addTabSelectInterceptorListener(listener: OnTabSelectInterceptorListener)
    interface OnTabSelectInterceptorListener {
        fun selectBeforeInterceptor(
            index: Int,
            prevInfo: BottomTabBean?,
            nextInfo: BottomTabBean
        ): Boolean {
            return false
        }

        fun selectAfterInterceptor(index: Int) {}
    }

}