package com.rafi.aplikasimanajemenproperti.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rafi.aplikasimanajemenproperti.ManajemenPropertiApp
import com.rafi.aplikasimanajemenproperti.ui.pemilik.viewmodel.HomePemilikViewModel
import com.rafi.aplikasimanajemenproperti.ui.pemilik.viewmodel.InsertPemilikViewModel

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
    }
}

fun CreationExtras.aplikasiManajemenProperti(): ManajemenPropertiApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ManajemenPropertiApp)