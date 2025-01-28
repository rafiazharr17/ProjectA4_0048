package com.rafi.aplikasimanajemenproperti.repository

import com.rafi.aplikasimanajemenproperti.model.AllPemilikResponse
import com.rafi.aplikasimanajemenproperti.model.Pemilik
import com.rafi.aplikasimanajemenproperti.service_api.PemilikService
import okio.IOException

interface PemilikRepository {
    suspend fun insertPemilik(pemilik: Pemilik)

    suspend fun getPemilik(): AllPemilikResponse

    suspend fun updatePemilik(idPemilik: Int, pemilik: Pemilik)

    suspend fun deletePemilik(idPemilik: Int)

    suspend fun getPemilikbyID(idPemilik: Int): Pemilik

    suspend fun getAllPemilik(): List<Pemilik>
}

class NetworkPemilikRepository(
    private val pemilikService: PemilikService
): PemilikRepository {
    override suspend fun insertPemilik(pemilik: Pemilik) {
        pemilikService.insertPemilik(pemilik)
    }

    override suspend fun updatePemilik(idPemilik: Int, pemilik: Pemilik) {
        pemilikService.updatePemilik(idPemilik, pemilik)
    }

    override suspend fun deletePemilik(idPemilik: Int) {
        try {
            val response = pemilikService.deletePemilik(idPemilik)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete pemilik. HTTP Status code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception){
            throw  e
        }
    }

    override suspend fun getPemilikbyID(idPemilik: Int): Pemilik {
        return pemilikService.getPemilikbyID(idPemilik).data
    }

    override suspend fun getPemilik(): AllPemilikResponse =
        pemilikService.getAllPemilik()

    override suspend fun getAllPemilik(): List<Pemilik> {
        return pemilikService.getAllPemilik().data
    }
}