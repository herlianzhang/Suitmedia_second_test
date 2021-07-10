package com.herlianzhang.suitmedia_first_test.vo

data class Guest(
    val id: Int,
    val name: String?,
    val birthdate: String?,
    val image: String? = "https://picsum.photos/200"
)