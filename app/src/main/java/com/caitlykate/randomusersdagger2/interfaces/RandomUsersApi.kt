package com.caitlykate.randomusersdagger2.interfaces

import com.caitlykate.randomusersdagger2.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUsersApi {

    @GET("api")
    fun getRandomUsers(@Query("results") size: Int): Call<ApiResponse>?
}