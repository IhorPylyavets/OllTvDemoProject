package com.example.olltvapplication.repository

import com.example.olltvapplication.data.OllTvDemoResponse
import com.example.olltvapplication.extension.toMainThread
import io.reactivex.Observable

class OllTvRepository(private val api: OllTvApi) {

    fun loadData(
        serialNumber: String,
        borderId: Long,
        direction: Int
    ): Observable<OllTvDemoResponse> = api.loadData(serialNumber, borderId, direction).toMainThread()
}
