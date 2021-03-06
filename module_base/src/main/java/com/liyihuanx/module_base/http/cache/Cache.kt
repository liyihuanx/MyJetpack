package com.liyihuanx.module_base.http.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author created by liyihuanx
 * @date 2021/9/8
 * @description: 类的描述
 */
@Entity(tableName = "tab_cache")
data class Cache(
    @PrimaryKey(autoGenerate = false)
    var key: String,

    var data: ByteArray? = null,

    var createTime: Long = System.currentTimeMillis(),

    //  60 * 60 * 1000L // 1小时
    var effectiveTime: Long? = null

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cache

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}