package com.herlianzhang.suitmedia_first_test.repository

import com.herlianzhang.suitmedia_first_test.api.ApiService
import com.herlianzhang.suitmedia_first_test.api.getResult
import javax.inject.Inject

class GuestRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getGuests() = getResult {
        apiService.getGuests()
    }
}