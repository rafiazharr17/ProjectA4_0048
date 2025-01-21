package com.rafi.aplikasimanajemenproperti

import android.app.Application
import com.rafi.aplikasimanajemenproperti.di.AppContainerPemilik
import com.rafi.aplikasimanajemenproperti.di.PemilikContainer

class ManajemenPropertiApp: Application() {
    lateinit var container: AppContainerPemilik
    override fun onCreate() {
        super.onCreate()
        container = PemilikContainer()
    }
}