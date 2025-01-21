package com.rafi.aplikasimanajemenproperti.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rafi.aplikasimanajemenproperti.ui.home.DestinasiHomePage
import com.rafi.aplikasimanajemenproperti.ui.home.HomePageView
import com.rafi.aplikasimanajemenproperti.ui.pemilik.view.DestinasiHomePemilik
import com.rafi.aplikasimanajemenproperti.ui.pemilik.view.DestinasiInsertPemilik
import com.rafi.aplikasimanajemenproperti.ui.pemilik.view.HomePemilikView
import com.rafi.aplikasimanajemenproperti.ui.pemilik.view.InsertPemilikView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController()
){
    NavHost(
        navController = navController,
        startDestination = DestinasiHomePage.route,
        modifier = Modifier,
    ){
        composable(DestinasiHomePage.route){
            HomePageView(
                onPropertiClick = {},
                onManajerPropertiClick = {},
                onJenisPropertiClick = {},
                onPemilikClick = {
                    navController.navigate(DestinasiHomePemilik.route)
                }
            )
        }

        composable(DestinasiHomePemilik.route){
            HomePemilikView(
                navigateToItemEntry = {
                    navController.navigate(DestinasiInsertPemilik.route)
                },
                onDetailClick = { //id ->
//                    navController.navigate("${DestinasiDetail.route}/$nim")
//                    println("PengelolaHalaman: nim = $nim")
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(DestinasiInsertPemilik.route){
            InsertPemilikView(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}