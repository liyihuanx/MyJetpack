package com.liyihuanx.myjetpack

import com.liyihuanx.module_base.http.BaseRepository
import kotlin.Any
import kotlin.String
import kotlin.coroutines.Continuation

/**
 * This file is generated by kapt, please do not edit this file */
open class ConfigRepository : BaseRepository<ConfigService>() {
    suspend fun config(page: String, p1: Continuation<in java.lang.String>): Any {
         return apiService!!.config(page,p1)
    }
}