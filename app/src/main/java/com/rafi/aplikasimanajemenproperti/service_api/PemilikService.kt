package com.rafi.aplikasimanajemenproperti.service_api

import com.rafi.aplikasimanajemenproperti.model.AllPemilikResponse
import com.rafi.aplikasimanajemenproperti.model.Pemilik
import com.rafi.aplikasimanajemenproperti.model.PemilikDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PemilikService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @POST("tambah")
    suspend fun insertPemilik(@Body pemilik: Pemilik)

    @GET(".")
    suspend fun getAllPemilik(): AllPemilikResponse

    @GET("{id_pemilik}")
    suspend fun getPemilikbyID(@Path("id_pemilik") idPemilik: Int): PemilikDetailResponse

    @PUT("{id_pemilik}")
    suspend fun updatePemilik(@Path("id_pemilik") idPemilik: Int, @Body pemilik: Pemilik)

    @DELETE("{id_pemilik}")
    suspend fun deletePemilik(@Path("id_pemilik") idPemilik: Int): Response<Void>
}