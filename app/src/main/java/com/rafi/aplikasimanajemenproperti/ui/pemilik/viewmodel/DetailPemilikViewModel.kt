package com.rafi.aplikasimanajemenproperti.ui.pemilik.viewmodel

import com.rafi.aplikasimanajemenproperti.model.Pemilik

sealed class DetailPemilikUiState {
    data class Success(val pemilik: Pemilik) : DetailPemilikUiState()
    object Error : DetailPemilikUiState()
    object Loading : DetailPemilikUiState()
}