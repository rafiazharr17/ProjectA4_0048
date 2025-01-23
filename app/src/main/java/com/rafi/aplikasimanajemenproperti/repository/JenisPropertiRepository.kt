package com.rafi.aplikasimanajemenproperti.repository

import com.rafi.aplikasimanajemenproperti.model.AllJenisResponse
import com.rafi.aplikasimanajemenproperti.model.JenisProperti
import com.rafi.aplikasimanajemenproperti.service_api.JenisPropertiService
import okio.IOException

interface JenisPropertiRepository {
    suspend fun insertJenisProperti(jenisProperti: JenisProperti)

    suspend fun getJenisProperti(): AllJenisResponse

    suspend fun updateJenisProperti(idJenis: Int, jenisProperti: JenisProperti)

    suspend fun deleteJenisProperti(idJenis: Int)

    suspend fun getJenisPropertibyID(idJenis: Int): JenisProperti
}

class NetworkJenisPropertiRepository(
    private val jenisPropertiService: JenisPropertiService
): JenisPropertiRepository {
    override suspend fun insertJenisProperti(jenisProperti: JenisProperti) {
        jenisPropertiService.insertJenisProperti(jenisProperti)
    }

    override suspend fun updateJenisProperti(idJenis: Int, jenisProperti: JenisProperti) {
        jenisPropertiService.updateJenisProperti(idJenis, jenisProperti)
    }

    override suspend fun deleteJenisProperti(idJenis: Int) {
        try {
            val response = jenisPropertiService.deleteJenisProperti(idJenis)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete ManajerProperti. HTTP Status code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception){
            throw  e
        }
    }

    override suspend fun getJenisPropertibyID(idJenis: Int): JenisProperti {
        return jenisPropertiService.getJenisPropertibyID(idJenis).data
    }

    override suspend fun getJenisProperti(): AllJenisResponse =
        jenisPropertiService.getAllJenisProperti()
}