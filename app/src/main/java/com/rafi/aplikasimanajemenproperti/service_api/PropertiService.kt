package com.rafi.aplikasimanajemenproperti.service_api

import com.rafi.aplikasimanajemenproperti.model.AllPropertiResponse
import com.rafi.aplikasimanajemenproperti.model.Properti
import com.rafi.aplikasimanajemenproperti.model.PropertiDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PropertiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @POST("tambah")
    suspend fun insertProperti(@Body properti: Properti)

    @GET(".")
    suspend fun getAllProperti(): AllPropertiResponse

    @GET("{id_properti}")
    suspend fun getPropertibyID(@Path("id_properti") idProperti: Int): PropertiDetailResponse

    @PUT("{id_properti}")
    suspend fun updateProperti(@Path("id_properti") idProperti: Int, @Body properti: Properti)

    @DELETE("{id_properti}")
    suspend fun deleteProperti(@Path("id_properti") idProperti: Int): Response<Void>
}