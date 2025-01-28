package com.rafi.aplikasimanajemenproperti.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.rafi.aplikasimanajemenproperti.repository.NetworkPropertiRepository
import com.rafi.aplikasimanajemenproperti.repository.PropertiRepository
import com.rafi.aplikasimanajemenproperti.service_api.PropertiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainerProperti{
    val propertiRepository: PropertiRepository
}

class PropertiContainer: AppContainerProperti{
    private val baseUrl = "http://10.0.2.2:3000/api/properti/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val propertiService: PropertiService by lazy {
        retrofit.create(PropertiService::class.java)
    }

    override val propertiRepository: PropertiRepository by lazy {
        NetworkPropertiRepository(propertiService)
    }
}