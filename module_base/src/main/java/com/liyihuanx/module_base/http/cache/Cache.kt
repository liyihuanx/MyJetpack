package com.liyihuanx.module_base.http.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author created by liyihuanx
 * @date 2021/9/8
 * @description: 类的描述
 */
@Entity(tableName = "tab_cache")
data class Cache (
    @PrimaryKey(autoGenerate = false)
    var key:String,

    var data: ByteArray

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cache

        if (key != other.key) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }
}