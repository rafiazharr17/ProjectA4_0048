package com.rafi.aplikasimanajemenproperti

import android.app.Application
import com.rafi.aplikasimanajemenproperti.di.AppContainerJenisProperti
import com.rafi.aplikasimanajemenproperti.di.AppContainerManajerProperti
import com.rafi.aplikasimanajemenproperti.di.AppContainerPemilik
import com.rafi.aplikasimanajemenproperti.di.JenisPropertiContainer
import com.rafi.aplikasimanajemenproperti.di.ManajerPropertiContainer
import com.rafi.aplikasimanajemenproperti.di.PemilikContainer

class ManajemenPropertiApp: Application() {
    lateinit var container: AppContainerPemilik
    lateinit var containerManajer: AppContainerManajerProperti
    lateinit var containerJenis: AppContainerJenisProperti
    override fun onCreate() {
        super.onCreate()
        container = PemilikContainer()
        containerManajer = ManajerPropertiContainer()
        containerJenis = JenisPropertiContainer()
    }
}