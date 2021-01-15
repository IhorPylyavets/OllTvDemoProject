package com.example.olltvapplication.repository

import com.example.olltvapplication.BuildConfig
import com.example.olltvapplication.data.OllTvDemoResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface OllTvApi {

    private companion object {
        const val BASE_URL = BuildConfig.BASE_URL
    }

    @GET("${BASE_URL}/demo")
    fun loadData(
        @Query("serial_number") serialNumber: String,
        @Query("borderId") borderId: Long,
        @Query("direction") direction: Int
    ): Observable<OllTvDemoResponse>
}
