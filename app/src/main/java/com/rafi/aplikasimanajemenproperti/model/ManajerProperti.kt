package com.rafi.aplikasimanajemenproperti.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllManajerResponse(
    val status: Boolean,
    val message: String,
    val data: List<ManajerProperti>
)

@Serializable
data class ManajerDetailResponse(
    val status: Boolean,
    val message: String,
    val data: ManajerProperti
)

@Serializable
data class ManajerProperti (
    @SerialName("id_manajer")
    val idManajer: Int,

    @SerialName("nama_manajer")
    val namaManajer: String,

    @SerialName("kontak_manajer")
    val kontakManajer: String,
)