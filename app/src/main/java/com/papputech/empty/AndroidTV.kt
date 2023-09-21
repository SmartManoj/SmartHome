package com.papputech.empty
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface AndroidTV {
    @GET("pp")
    suspend fun pnpTV(): Response<Unit>

}
