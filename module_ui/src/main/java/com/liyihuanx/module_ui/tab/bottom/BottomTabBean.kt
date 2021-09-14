package com.liyihuanx.module_ui.tab.bottom

/**
 * @author created by liyihuanx
 * @date 2021/8/27
 * @description: 类的描述
 */
data class BottomTabBean(
    val itemName: String,
    val selectIcon: Int? = null,
    val normalIcon: Int,
    val selectColor: Int?= null,
    val normalColor: Int?= null,
    val start: Boolean? = false,
    val textFont: Int? = null
)