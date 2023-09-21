package com.papputech.empty
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface AndroidTV {
    @GET("pause")
    suspend fun pauseTV(): Response<Unit>

    @GET("play")
    suspend fun playTV(): Response<Unit>

}
