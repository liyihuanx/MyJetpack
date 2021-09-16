package com.liyihuan.module_common.bean

import java.io.Serializable

/**
 * @ClassName: ChapterBean
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/4/15 22:19
 */
data class ChapterBean(
    var courseId: Int? = 0,
    var id: Int? = 0,
    var name: String? = null,
    var order: Int? = 0,
    var parentChapterId: Int? = 0,
    var isUserControlSetTop: Boolean? = false,
    var visible: Int? = 0,
    var children: List<*>? = null
) : Serializable {
    /**
     * children : []
     * courseId : 13
     * id : 408
     * name : 鸿洋
     * order : 190000
     * parentChapterId : 407
     * userControlSetTop : false
     * visible : 1
     */
}