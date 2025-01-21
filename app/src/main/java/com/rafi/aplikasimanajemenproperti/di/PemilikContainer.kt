package com.rafi.aplikasimanajemenproperti.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.rafi.aplikasimanajemenproperti.repository.NetworkPemilikRepository
import com.rafi.aplikasimanajemenproperti.repository.PemilikRepository
import com.rafi.aplikasimanajemenproperti.service_api.PemilikService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainerPemilik{
    val pemilikRepository: PemilikRepository
}

class PemilikContainer: AppContainerPemilik{
    private val baseUrl = "http://10.0.2.2:3000/api/pemilik/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val pemilikService: PemilikService by lazy {
        retrofit.create(PemilikService::class.java)
    }

    override val pemilikRepository: PemilikRepository by lazy {
        NetworkPemilikRepository(pemilikService)
    }
}