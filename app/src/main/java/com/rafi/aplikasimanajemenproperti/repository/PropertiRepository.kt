package com.rafi.aplikasimanajemenproperti.repository

import com.rafi.aplikasimanajemenproperti.model.AllPropertiResponse
import com.rafi.aplikasimanajemenproperti.model.Properti
import com.rafi.aplikasimanajemenproperti.service_api.PropertiService
import okio.IOException

interface PropertiRepository {
    suspend fun insertProperti(properti: Properti)

    suspend fun getProperti(): AllPropertiResponse

    suspend fun updateProperti(idProperti: Int, properti: Properti)

    suspend fun deleteProperti(idProperti: Int)

    suspend fun getPropertibyID(idProperti: Int): Properti
}

class NetworkPropertiRepository(
    private val propertiService: PropertiService
): PropertiRepository {
    override suspend fun insertProperti(properti: Properti) {
        propertiService.insertProperti(properti)
    }

    override suspend fun updateProperti(idProperti: Int, properti: Properti) {
        propertiService.updateProperti(idProperti, properti)
    }

    override suspend fun deleteProperti(idProperti: Int) {
        try {
            val response = propertiService.deleteProperti(idProperti)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete properti. HTTP Status code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception){
            throw  e
        }
    }

    override suspend fun getPropertibyID(idProperti: Int): Properti {
        return propertiService.getPropertibyID(idProperti).data
    }

    override suspend fun getProperti(): AllPropertiResponse =
        propertiService.getAllProperti()
}