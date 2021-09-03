package liyihuan.app.android.module_ui.tab.bottom

/**
 * @author created by liyihuanx
 * @date 2021/8/27
 * @description: 类的描述
 */
interface IBottomTab : IBottomLayout.OnTabSelectedListener {

    fun setTabInfo(
        info: BottomTabBean,
        defaultNormalColor: Int,
        defaultSelectColor: Int
    )

}