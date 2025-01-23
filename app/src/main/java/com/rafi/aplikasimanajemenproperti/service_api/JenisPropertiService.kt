package com.rafi.aplikasimanajemenproperti.service_api

import com.rafi.aplikasimanajemenproperti.model.AllJenisResponse
import com.rafi.aplikasimanajemenproperti.model.JenisDetailResponse
import com.rafi.aplikasimanajemenproperti.model.JenisProperti
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface JenisPropertiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @POST("tambah")
    suspend fun insertJenisProperti(@Body jenisProperti: JenisProperti)

    @GET(".")
    suspend fun getAllJenisProperti(): AllJenisResponse

    @GET("{id_jenis}")
    suspend fun getJenisPropertibyID(@Path("id_jenis") idJenis: Int): JenisDetailResponse

    @PUT("{id_jenis}")
    suspend fun updateJenisProperti(@Path("id_jenis") idJenis: Int, @Body jenisProperti: JenisProperti)

    @DELETE("{id_jenis}")
    suspend fun deleteJenisProperti(@Path("id_jenis") idJenis: Int): Response<Void>
}