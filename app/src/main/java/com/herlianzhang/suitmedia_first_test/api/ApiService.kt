package com.herlianzhang.suitmedia_first_test.api

import com.herlianzhang.suitmedia_first_test.vo.Guest
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("")
    suspend fun getGuests(): Response<Guest>
}