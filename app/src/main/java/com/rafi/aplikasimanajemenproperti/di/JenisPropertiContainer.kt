package com.rafi.aplikasimanajemenproperti.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.rafi.aplikasimanajemenproperti.repository.JenisPropertiRepository
import com.rafi.aplikasimanajemenproperti.repository.NetworkJenisPropertiRepository
import com.rafi.aplikasimanajemenproperti.service_api.JenisPropertiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainerJenisProperti{
    val jenisPropertiRepository: JenisPropertiRepository
}

class JenisPropertiContainer: AppContainerJenisProperti{
    private val baseUrl = "http://10.0.2.2:3000/api/jenis_properti/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val jenisPropertiService: JenisPropertiService by lazy {
        retrofit.create(JenisPropertiService::class.java)
    }

    override val jenisPropertiRepository: JenisPropertiRepository by lazy {
        NetworkJenisPropertiRepository(jenisPropertiService)
    }
}