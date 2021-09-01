package liyihuan.app.android.module_ui.tab.bottom

/**
 * @author created by liyihuanx
 * @date 2021/8/27
 * @description: 类的描述
 */
data class BottomTabBean(
    val itemName: String,
    val selectIcon: Int? = null,
    val normalIcon: Int,
    val selectColor: Int,
    val normalColor: Int,
    val start: Boolean? = false,
    val textFont: Int? = null
)