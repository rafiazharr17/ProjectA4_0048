package com.rafi.aplikasimanajemenproperti.service_api

import com.rafi.aplikasimanajemenproperti.model.AllManajerResponse
import com.rafi.aplikasimanajemenproperti.model.ManajerDetailResponse
import com.rafi.aplikasimanajemenproperti.model.ManajerProperti
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ManajerPropertiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @POST("tambah")
    suspend fun insertManajerProperti(@Body manajerProperti: ManajerProperti)

    @GET(".")
    suspend fun getAllManajerProperti(): AllManajerResponse

    @GET("{id_manajer}")
    suspend fun getManajerPropertibyID(@Path("id_manajer") idManajer: Int): ManajerDetailResponse

    @PUT("{id_manajer}")
    suspend fun updateManajerProperti(@Path("id_manajer") idManajer: Int, @Body manajerProperti: ManajerProperti)

    @DELETE("{id_manajer}")
    suspend fun deleteManajerProperti(@Path("id_manajer") idManajer: Int): Response<Void>
}