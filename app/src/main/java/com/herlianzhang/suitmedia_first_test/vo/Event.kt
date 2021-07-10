package com.herlianzhang.suitmedia_first_test.vo

data class Event(
    val id: Int,
    val image: String?,
    val name: String?,
    val date: String?,
    val tag: List<String>?,
    val desc: String?,
    val lat: Double?,
    val lng: Double?
)
