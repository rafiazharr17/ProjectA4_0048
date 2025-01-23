package com.rafi.aplikasimanajemenproperti.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllJenisResponse(
    val status: Boolean,
    val message: String,
    val data: List<JenisProperti>
)

@Serializable
data class JenisDetailResponse(
    val status: Boolean,
    val message: String,
    val data: JenisProperti
)

@Serializable
data class JenisProperti (
    @SerialName("id_jenis")
    val idJenis: Int,

    @SerialName("nama_jenis")
    val namaJenis: String,

    @SerialName("deskripsi_jenis")
    val deskripsiJenis: String,
)