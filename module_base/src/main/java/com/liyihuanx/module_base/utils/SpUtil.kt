package com.liyihuanx.module_base.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import androidx.collection.SimpleArrayMap
import com.liyihuanx.module_base.utils.AppContext.getApplicationContext
import java.io.File
import java.util.*

/**
 * @author created by liyihuanx
 * @date 2021/10/12
 * @description: 类的描述
 */
class SpUtil private constructor(
    private var shrefName: String = "default_sharedpreferences",
    mode: Int = Context.MODE_PRIVATE
) {
    private var shref: SharedPreferences =
        getApplicationContext().getSharedPreferences(shrefName, mode)
    private var shrefDir: String = getShrefDir(getApplicationContext())


    /**
     * 获取SharedPreferences文件的目录路径
     *
     * @param context
     * @return
     */
    private fun getShrefDir(context: Context): String {
        return (File.separator + "data" + File.separator + "data" + File.separator + context.packageName
                + File.separator + "shared_prefs")
    }

    /**
     * 判断sp文件是否存在¬
     *
     * @return
     */
    val isSPFileExists: Boolean
        get() {
            val file = File(shrefDir, "$shrefName.xml")
            return file.exists() ?: false
        }

    /**
     * 获取配置文件上次修改时间
     *
     * @return
     */
    fun lastModified(): Long {
        val file = File(shrefDir, "$shrefName.xml")
        return if (file != null && file.exists()) {
            file.lastModified()
        } else 0
    }

    // SharedPreference 相关修改使用 apply 方法进行提交会先写入内存，然后异步写入磁盘，commit
    // 方法是直接写入磁盘。如果频繁操作的话 apply 的性能会优于 commit，apply会将最后修改内容写入磁盘。
    // 但是如果希望立刻获取存储操作的结果，并据此做相应的其他操作，应当使用 commit。
    /**
     * SP中写入string
     *
     * @param key
     * @param value
     */
    @JvmOverloads
    fun saveData(key: String, value: String, commit: Boolean = false) {
        val editor = shref.edit()
        editor.putString(key, value)
        if (commit) {
            editor.commit()
        } else {
            editor.apply()
        }
    }
    /**
     * SP中写入int
     *
     * @param key
     * @param value
     * @param commit
     */
    /**
     * SP中写入int
     *
     * @param key
     * @param value
     */
    @JvmOverloads
    fun saveData(key: String, value: Int, commit: Boolean = false) {
        val editor = shref!!.edit()
        editor.putInt(key, value)
        if (commit) {
            editor.commit()
        } else {
            editor.apply()
        }
    }
    /**
     * SP中写入boolean
     *
     * @param key
     * @param value
     * @param commit
     */
    /**
     * SP中写入boolean
     *
     * @param key
     * @param value
     */
    @JvmOverloads
    fun saveData(key: String, value: Boolean, commit: Boolean = false) {
        val editor = shref.edit()
        editor.putBoolean(key, value)
        if (commit) {
            editor.commit()
        } else {
            editor.apply()
        }
    }
    /**
     * SP中写入long
     *
     * @param key
     * @param value
     * @param commit
     */
    /**
     * SP中写入long
     *
     * @param key
     * @param value
     */
    @JvmOverloads
    fun saveData(key: String, value: Long, commit: Boolean = false) {
        val editor = shref.edit()
        editor.putLong(key, value)
        if (commit) {
            editor.commit()
        } else {
            editor.apply()
        }
    }
    /**
     * SP中写入float
     *
     * @param key
     * @param value
     * @param commit
     */
    /**
     * SP中写入float
     *
     * @param key
     * @param value
     */
    @JvmOverloads
    fun saveData(key: String, value: Float, commit: Boolean = false) {
        val editor = shref.edit()
        editor.putFloat(key, value)
        if (commit) {
            editor.commit()
        } else {
            editor.apply()
        }
    }
    /**
     * SP中写入set<string>
     *
     * @param key
     * @param value
     */
    @JvmOverloads
    fun saveData(key: String, value: Set<String?>, commit: Boolean = false) {
        val editor = shref.edit()
        editor.putStringSet(key, value)
        if (commit) {
            editor.commit()
        } else {
            editor.apply()
        }
    }
    /**
     * SP中读取string
     *
     * @param key
     * @param defaultValue
     * @return
     */
    @JvmOverloads
    fun readString(key: String, defaultValue: String? = null): String? {
        return shref.getString(key, defaultValue)
    }

    /**
     * SP中读取int
     *
     * @param key
     * @param defalValue
     * @return
     */
    @JvmOverloads
    fun readInt(key: String, defaultValue: Int = 0): Int {
        return shref.getInt(key, defaultValue)
    }
    /**
     * SP中读取boolean
     *
     * @param key
     * @param defaultValue
     * @return
     */
    /**
     * SP中读取boolean
     *
     * @param key
     * @return
     */
    @JvmOverloads
    fun readBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return shref.getBoolean(key, defaultValue)
    }
    /**
     * SP中读取long
     *
     * @param key
     * @param defaultValue
     * @return
     */
    @JvmOverloads
    fun readLong(key: String, defaultValue: Long = 0): Long {
        return shref.getLong(key, defaultValue)
    }
    /**
     * SP中读取float
     *
     * @param key
     * @param defaultValue
     * @return
     */
    @JvmOverloads
    fun readFloat(key: String, defaultValue: Float = 0f): Float {
        return shref.getFloat(key, defaultValue)
    }

    /**
     * SP中读取set<string>
     *
     * @param key
     * @return
     */
    @JvmOverloads
    fun readSetString(key: String, defaultValue: Set<String?>?): HashSet<String> {
        return HashSet(shref.getStringSet(key, defaultValue))
    }

    /**
     * SP中是否包含指定的key
     *
     * @param key
     * @return
     */
    operator fun contains(key: String?): Boolean {
        return shref.contains(key)
    }
    /**
     * SP中移除指定的key
     *
     * @param key
     * @param commit
     */
    @JvmOverloads
    fun remove(key: String?, commit: Boolean = false) {
        val editor = shref!!.edit()
        editor.remove(key)
        if (commit) {
            editor.commit()
        } else {
            editor.apply()
        }
    }

    /**
     * 清除sp中的数据
     */
    fun clear() {
        val editor = shref.edit()
        editor.clear()
        editor.apply()
    }

    fun registerOnSharedPreferenceChangeListener(
        listener: OnSharedPreferenceChangeListener?,
        regist: Boolean
    ) {
        if (regist) {
            shref.registerOnSharedPreferenceChangeListener(listener)
        } else {
            shref.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    companion object {
        private val SP_MAP = SimpleArrayMap<String, SpUtil>()
        operator fun get(spName: String): SpUtil {
            var sharefUtil = SP_MAP[spName]
            if (sharefUtil == null) {
                synchronized(SpUtil::class.java) {
                    sharefUtil = SpUtil(spName)
                    SP_MAP.put(spName, sharefUtil)
                }
            }
            return sharefUtil!!
        }

        operator fun get(spName: String, mode: Int): SpUtil {
            var sharefUtil = SP_MAP[spName]
            if (sharefUtil == null) {
                synchronized(SpUtil::class.java) {
                    sharefUtil = SpUtil(spName, mode)
                    SP_MAP.put(spName, sharefUtil)
                }
            }
            return sharefUtil!!
        }
    }


}