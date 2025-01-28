package com.rafi.aplikasimanajemenproperti.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllPropertiResponse(
    val status: Boolean,
    val message: String,
    val data: List<Properti>
)

@Serializable
data class PropertiDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Properti
)

@Serializable
data class Properti (
    @SerialName("id_properti")
    val idProperti: Int,

    @SerialName("nama_properti")
    val namaProperti: String,

    @SerialName("deskripsi_properti")
    val deskripsiProperti: String,

    @SerialName("lokasi")
    val lokasi: String,

    @SerialName("harga")
    val harga: Int,

    @SerialName("status_properti")
    val statusProperti: String,

    @SerialName("id_jenis")
    val idJenis: Int,

    @SerialName("id_pemilik")
    val idPemilik: Int,

    @SerialName("id_manajer")
    val idManajer: Int
)