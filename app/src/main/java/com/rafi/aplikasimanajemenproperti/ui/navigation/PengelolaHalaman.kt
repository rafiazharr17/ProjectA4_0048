package com.rafi.aplikasimanajemenproperti.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rafi.aplikasimanajemenproperti.ui.jenis_properti.view.DestinasiDetailJenis
import com.rafi.aplikasimanajemenproperti.ui.jenis_properti.view.DestinasiHomeJenis
import com.rafi.aplikasimanajemenproperti.ui.jenis_properti.view.DestinasiInsertJenis
import com.rafi.aplikasimanajemenproperti.ui.jenis_properti.view.DestinasiUpdateJenis
import com.rafi.aplikasimanajemenproperti.ui.jenis_properti.view.DetailJenisView
import com.rafi.aplikasimanajemenproperti.ui.jenis_properti.view.HomeJenisView
import com.rafi.aplikasimanajemenproperti.ui.jenis_properti.view.InsertJenisView
import com.rafi.aplikasimanajemenproperti.ui.jenis_properti.view.UpdateJenisView
import com.rafi.aplikasimanajemenproperti.ui.manajer_properti.view.DestinasiDetailManajer
import com.rafi.aplikasimanajemenproperti.ui.manajer_properti.view.DestinasiHomeManajer
import com.rafi.aplikasimanajemenproperti.ui.manajer_properti.view.DestinasiInsertManajer
import com.rafi.aplikasimanajemenproperti.ui.manajer_properti.view.DestinasiUpdateManajer
import com.rafi.aplikasimanajemenproperti.ui.manajer_properti.view.DetailManajerView
import com.rafi.aplikasimanajemenproperti.ui.manajer_properti.view.HomeManajerView
import com.rafi.aplikasimanajemenproperti.ui.manajer_properti.view.InsertManajerView
import com.rafi.aplikasimanajemenproperti.ui.manajer_properti.view.UpdateManajerView
import com.rafi.aplikasimanajemenproperti.ui.pemilik.view.DestinasiDetailPemilik
import com.rafi.aplikasimanajemenproperti.ui.pemilik.view.DestinasiHomePemilik
import com.rafi.aplikasimanajemenproperti.ui.pemilik.view.DestinasiInsertPemilik
import com.rafi.aplikasimanajemenproperti.ui.pemilik.view.DestinasiUpdatePemilik
import com.rafi.aplikasimanajemenproperti.ui.pemilik.view.DetailPemilikView
import com.rafi.aplikasimanajemenproperti.ui.pemilik.view.HomePemilikView
import com.rafi.aplikasimanajemenproperti.ui.pemilik.view.InsertPemilikView
import com.rafi.aplikasimanajemenproperti.ui.pemilik.view.UpdatePemilikView
import com.rafi.aplikasimanajemenproperti.ui.properti.view.DestinasiDetailProperti
import com.rafi.aplikasimanajemenproperti.ui.properti.view.DestinasiHomeProperti
import com.rafi.aplikasimanajemenproperti.ui.properti.view.DestinasiInsertProperti
import com.rafi.aplikasimanajemenproperti.ui.properti.view.DestinasiUpdateProperti
import com.rafi.aplikasimanajemenproperti.ui.properti.view.DetailPropertiView
import com.rafi.aplikasimanajemenproperti.ui.properti.view.HomePropertiView
import com.rafi.aplikasimanajemenproperti.ui.properti.view.InsertPropertiView
import com.rafi.aplikasimanajemenproperti.ui.properti.view.UpdatePropertiView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController()
){
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeProperti.route,
        modifier = Modifier,
    ){
        composable(DestinasiHomePemilik.route){
            HomePemilikView(
                navigateToItemEntry = {
                    navController.navigate(DestinasiInsertPemilik.route)
                },
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetailPemilik.route}/$id")
                    println("PengelolaHalaman: idPemilik = $id")
                },
                onEditClick = { id ->
                    navController.navigate("${DestinasiUpdatePemilik.route}/$id")
                    println("PengelolaHalaman: idPemilik = $id")
                },
                navigateManajer = {
                    navController.navigate(DestinasiHomeManajer.route)
                },
                navigateProperti = {
                    navController.navigate(DestinasiHomeProperti.route)
                },
                activeMenu = "Pemilik"
            )
        }

        composable(DestinasiInsertPemilik.route){
            InsertPemilikView(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable (
            DestinasiDetailPemilik.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailPemilik.ID) {
                    type = NavType.IntType
                }
            )
        ){
            val id = it.arguments?.getInt(DestinasiDetailPemilik.ID)

            id?.let { id ->
                DetailPemilikView(
                    navigateBack = {
                        navController.navigate(DestinasiHomePemilik.route) {
                            popUpTo(DestinasiHomePemilik.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }

        composable(
            DestinasiUpdatePemilik.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdatePemilik.ID){
                    type = NavType.IntType
                }
            )
        ){
            val id = it.arguments?.getInt(DestinasiUpdatePemilik.ID)
            id?.let { id ->
                UpdatePemilikView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onNavigate = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(DestinasiHomeManajer.route){
            HomeManajerView(
                navigateToItemEntry = {
                    navController.navigate(DestinasiInsertManajer.route)
                },
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetailManajer.route}/$id")
                    println("PengelolaHalaman: idManajer = $id")
                },
                onEditClick = { id ->
                    navController.navigate("${DestinasiUpdateManajer.route}/$id")
                    println("PengelolaHalaman: idManajer = $id")
                },
                navigatePemilik = {
                    navController.navigate(DestinasiHomePemilik.route)
                },
                navigateProperti = {
                    navController.navigate(DestinasiHomeProperti.route)
                },
                activeMenu = "Manajer"
            )
        }

        composable(DestinasiInsertManajer.route){
            InsertManajerView(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            DestinasiUpdateManajer.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateManajer.ID){
                    type = NavType.IntType
                }
            )
        ){
            val id = it.arguments?.getInt(DestinasiUpdateManajer.ID)
            id?.let { id ->
                UpdateManajerView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onNavigate = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable (
            DestinasiDetailManajer.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailManajer.ID) {
                    type = NavType.IntType
                }
            )
        ){
            val id = it.arguments?.getInt(DestinasiDetailManajer.ID)

            id?.let { id ->
                DetailManajerView(
                    navigateBack = {
                        navController.navigate(DestinasiHomeManajer.route) {
                            popUpTo(DestinasiHomeManajer.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }

        composable(DestinasiHomeJenis.route){
            HomeJenisView(
                navigateToItemEntry = {
                    navController.navigate(DestinasiInsertJenis.route)
                },
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetailJenis.route}/$id")
                    println("PengelolaHalaman: idJenis = $id")},
                onEditClick = { id ->
                    navController.navigate("${DestinasiUpdateJenis.route}/$id")
                    println("PengelolaHalaman: idJenis = $id")
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(DestinasiInsertJenis.route){
            InsertJenisView(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            DestinasiUpdateJenis.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateJenis.ID){
                    type = NavType.IntType
                }
            )
        ){
            val id = it.arguments?.getInt(DestinasiUpdateJenis.ID)
            id?.let { id ->
                UpdateJenisView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onNavigate = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable (
            DestinasiDetailJenis.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailJenis.ID) {
                    type = NavType.IntType
                }
            )
        ){
            val id = it.arguments?.getInt(DestinasiDetailJenis.ID)

            id?.let { id ->
                DetailJenisView(
                    navigateBack = {
                        navController.navigate(DestinasiHomeJenis.route) {
                            popUpTo(DestinasiHomeJenis.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }

        composable(DestinasiInsertProperti.route){
            InsertPropertiView(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(DestinasiHomeProperti.route){
            HomePropertiView(
                navigateToItemEntry = {
                    navController.navigate(DestinasiInsertProperti.route)
                },
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetailProperti.route}/$id")
                    println("PengelolaHalaman: idProperti = $id")
                },
                onEditClick = { id ->
                    navController.navigate("${DestinasiUpdateProperti.route}/$id")
                    println("PengelolaHalaman: idProperti = $id")
                },
                navigatePemilik = {
                    navController.navigate(DestinasiHomePemilik.route)
                },
                navigateManajer = {
                    navController.navigate(DestinasiHomeManajer.route)
                },
                activeMenu = "Properti"
            )
        }

        composable (
            DestinasiDetailProperti.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailProperti.ID) {
                    type = NavType.IntType
                }
            )
        ){
            val id = it.arguments?.getInt(DestinasiDetailProperti.ID)

            id?.let { id ->
                DetailPropertiView(
                    navigateBack = {
                        navController.popBackStack()
                    },
                    navigateToJenis = {
                        navController.navigate(DestinasiHomeJenis.route)
                    }
                )
            }
        }

        composable(
            DestinasiUpdateProperti.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateProperti.ID){
                    type = NavType.IntType
                }
            )
        ){
            val id = it.arguments?.getInt(DestinasiUpdateProperti.ID)
            id?.let { id ->
                UpdatePropertiView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onNavigate = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}