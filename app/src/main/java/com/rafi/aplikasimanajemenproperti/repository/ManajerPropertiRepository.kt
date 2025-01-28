package com.rafi.aplikasimanajemenproperti.repository

import com.rafi.aplikasimanajemenproperti.model.AllManajerResponse
import com.rafi.aplikasimanajemenproperti.model.ManajerProperti
import com.rafi.aplikasimanajemenproperti.service_api.ManajerPropertiService
import okio.IOException

interface ManajerPropertiRepository {
    suspend fun insertManajerProperti(manajerProperti: ManajerProperti)

    suspend fun getManajerProperti(): AllManajerResponse

    suspend fun updateManajerProperti(idManajer: Int, manajerProperti: ManajerProperti)

    suspend fun deleteManajerProperti(idManajer: Int)

    suspend fun getManajerPropertibyID(idManajer: Int): ManajerProperti

    suspend fun getAllManajer(): List<ManajerProperti>
}

class NetworkManajerPropertiRepository(
    private val manajerPropertiService: ManajerPropertiService
): ManajerPropertiRepository {
    override suspend fun insertManajerProperti(manajerProperti: ManajerProperti) {
        manajerPropertiService.insertManajerProperti(manajerProperti)
    }

    override suspend fun updateManajerProperti(idManajer: Int, manajerProperti: ManajerProperti) {
        manajerPropertiService.updateManajerProperti(idManajer, manajerProperti)
    }

    override suspend fun deleteManajerProperti(idManajer: Int) {
        try {
            val response = manajerPropertiService.deleteManajerProperti(idManajer)
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

    override suspend fun getManajerPropertibyID(idManajer: Int): ManajerProperti {
        return manajerPropertiService.getManajerPropertibyID(idManajer).data
    }

    override suspend fun getManajerProperti(): AllManajerResponse =
        manajerPropertiService.getAllManajerProperti()

    override suspend fun getAllManajer(): List<ManajerProperti> {
        return manajerPropertiService.getAllManajerProperti().data
    }
}