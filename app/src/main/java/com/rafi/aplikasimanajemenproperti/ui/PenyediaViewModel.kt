package com.rafi.aplikasimanajemenproperti.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rafi.aplikasimanajemenproperti.ManajemenPropertiApp
import com.rafi.aplikasimanajemenproperti.ui.jenis_properti.viewmodel.DetailJenisViewModel
import com.rafi.aplikasimanajemenproperti.ui.jenis_properti.viewmodel.HomeJenisViewModel
import com.rafi.aplikasimanajemenproperti.ui.jenis_properti.viewmodel.InsertJenisViewModel
import com.rafi.aplikasimanajemenproperti.ui.jenis_properti.viewmodel.UpdateJenisViewModel
import com.rafi.aplikasimanajemenproperti.ui.manajer_properti.viewmodel.DetailManajerViewModel
import com.rafi.aplikasimanajemenproperti.ui.manajer_properti.viewmodel.HomeManajerViewModel
import com.rafi.aplikasimanajemenproperti.ui.manajer_properti.viewmodel.InsertManajerViewModel
import com.rafi.aplikasimanajemenproperti.ui.manajer_properti.viewmodel.UpdateManajerViewModel
import com.rafi.aplikasimanajemenproperti.ui.pemilik.viewmodel.DetailPemilikViewModel
import com.rafi.aplikasimanajemenproperti.ui.pemilik.viewmodel.HomePemilikViewModel
import com.rafi.aplikasimanajemenproperti.ui.pemilik.viewmodel.InsertPemilikViewModel
import com.rafi.aplikasimanajemenproperti.ui.pemilik.viewmodel.UpdatePemilikViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomePemilikViewModel(
                aplikasiManajemenProperti().container.pemilikRepository
            )
        }

        initializer {
            InsertPemilikViewModel(
                aplikasiManajemenProperti().container.pemilikRepository
            )
        }

        initializer {
            DetailPemilikViewModel(
                createSavedStateHandle(),
                aplikasiManajemenProperti().container.pemilikRepository
            )
        }

        initializer {
            UpdatePemilikViewModel(
                createSavedStateHandle(),
                aplikasiManajemenProperti().container.pemilikRepository
            )
        }

        initializer {
            HomeManajerViewModel(
                aplikasiManajemenProperti().containerManajer.manajerPropertiRepository
            )
        }

        initializer {
            InsertManajerViewModel(
                aplikasiManajemenProperti().containerManajer.manajerPropertiRepository
            )
        }

        initializer {
            UpdateManajerViewModel(
                createSavedStateHandle(),
                aplikasiManajemenProperti().containerManajer.manajerPropertiRepository
            )
        }

        initializer {
            DetailManajerViewModel(
                createSavedStateHandle(),
                aplikasiManajemenProperti().containerManajer.manajerPropertiRepository
            )
        }

        initializer {
            HomeJenisViewModel(
                aplikasiManajemenProperti().containerJenis.jenisPropertiRepository
            )
        }

        initializer {
            InsertJenisViewModel(
                aplikasiManajemenProperti().containerJenis.jenisPropertiRepository
            )
        }

        initializer {
            UpdateJenisViewModel(
                createSavedStateHandle(),
                aplikasiManajemenProperti().containerJenis.jenisPropertiRepository
            )
        }

        initializer {
            DetailJenisViewModel(
                createSavedStateHandle(),
                aplikasiManajemenProperti().containerJenis.jenisPropertiRepository
            )
        }
    }
}

fun CreationExtras.aplikasiManajemenProperti(): ManajemenPropertiApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ManajemenPropertiApp)