package com.zhaoyuanjie.adphoneblocker

/**
 * Models
 * Created by zhaoyuanjie on 15/12/26.
 */

data class Location(val province: String, val city: String, val operators: String)

data class PhoneInfo(val name: String?, val count: Int?, val type: String?, val location: Location?)

data class QueryResult(val response: Map<String, PhoneInfo>)