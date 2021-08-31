package liyihuan.app.android.module_ui.tab.bottom

/**
 * @ClassName: IBottomLayout
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/8/27 23:52
 */
interface IBottomLayout {

    fun bindBottomTabData(data: ArrayList<BottomTabBean>)


    fun addTabSelectedChangeListener(listener: OnTabSelectedListener)


    interface OnTabSelectedListener {
        fun onTabSelectedChange(index: Int, prevInfo: BottomTabBean?, nextInfo: BottomTabBean)
    }

}