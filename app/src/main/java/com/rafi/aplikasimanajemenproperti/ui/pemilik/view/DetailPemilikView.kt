package com.rafi.aplikasimanajemenproperti.ui.pemilik.view

import com.rafi.aplikasimanajemenproperti.ui.navigation.DestinasiNavigasi

object DestinasiDetailPemilik : DestinasiNavigasi {
    override val route = "detail"
    override val titleRes = "Detail Pemilik"
    const val ID = "idPemilik"
    val routeWithArg = "$route/{$ID}"
}