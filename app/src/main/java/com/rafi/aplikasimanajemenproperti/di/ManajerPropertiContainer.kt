package com.rafi.aplikasimanajemenproperti.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.rafi.aplikasimanajemenproperti.repository.ManajerPropertiRepository
import com.rafi.aplikasimanajemenproperti.repository.NetworkManajerPropertiRepository
import com.rafi.aplikasimanajemenproperti.service_api.ManajerPropertiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainerManajerProperti{
    val manajerPropertiRepository: ManajerPropertiRepository
}

class ManajerPropertiContainer: AppContainerManajerProperti{
    private val baseUrl = "http://10.0.2.2:3000/api/manajer_properti/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val manajerPropertiService: ManajerPropertiService by lazy {
        retrofit.create(ManajerPropertiService::class.java)
    }

    override val manajerPropertiRepository: ManajerPropertiRepository by lazy {
        NetworkManajerPropertiRepository(manajerPropertiService)
    }
}