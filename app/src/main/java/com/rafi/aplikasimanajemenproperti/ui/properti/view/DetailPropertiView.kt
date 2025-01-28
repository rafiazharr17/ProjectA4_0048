package com.rafi.aplikasimanajemenproperti.ui.properti.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafi.aplikasimanajemenproperti.model.JenisProperti
import com.rafi.aplikasimanajemenproperti.model.ManajerProperti
import com.rafi.aplikasimanajemenproperti.model.Pemilik
import com.rafi.aplikasimanajemenproperti.model.Properti
import com.rafi.aplikasimanajemenproperti.ui.PenyediaViewModel
import com.rafi.aplikasimanajemenproperti.ui.customwidget.CostumeTopAppBar
import com.rafi.aplikasimanajemenproperti.ui.navigation.DestinasiNavigasi
import com.rafi.aplikasimanajemenproperti.ui.properti.viewmodel.DetailPropertiUiState
import com.rafi.aplikasimanajemenproperti.ui.properti.viewmodel.DetailPropertiViewModel
import java.text.NumberFormat
import java.util.Locale

object DestinasiDetailProperti : DestinasiNavigasi {
    override val route = "detail_properti"
    override val titleRes = "Detail Properti"
    const val ID = "idProperti"
    val routeWithArg = "$route/{$ID}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPropertiView(
    navigateBack: () -> Unit,
    navigateToJenis: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailPropertiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailProperti.titleRes,
                canNavigateBack = true,
                canRefresh = false,
                onRefresh = {},
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton (
                onClick = navigateToJenis,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Home Jenis")
                    Text(
                        text = "Jenis Properti",
                        fontSize = 20.sp
                    )
                }
            }
        }
    ) { innerPadding ->
        DetailPropertiStatus(
            modifier = Modifier.padding(innerPadding),
            detailPropertiUiState = viewModel.propertiDetailState,
            retryAction = { viewModel.getPropertibyID() },
            jenisList = viewModel.jenisList,
            pemilikList = viewModel.pemilikList,
            manajerList = viewModel.manajerList
        )
    }
}

@Composable
fun DetailPropertiStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    jenisList: List<JenisProperti>,
    pemilikList: List<Pemilik>,
    manajerList: List<ManajerProperti>,
    detailPropertiUiState: DetailPropertiUiState
) {
    when (detailPropertiUiState) {
        is DetailPropertiUiState.Loading -> com.rafi.aplikasimanajemenproperti.ui.properti.view.OnLoading(
            modifier = modifier.fillMaxSize()
        )

        is DetailPropertiUiState.Success -> {
            if (detailPropertiUiState.properti.idProperti.toString().isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailProperti(
                    properti = detailPropertiUiState.properti,
                    modifier = modifier.fillMaxWidth(),
                    jenisList = jenisList,
                    pemilikList = pemilikList,
                    manajerList = manajerList
                )
            }
        }

        is DetailPropertiUiState.Error -> com.rafi.aplikasimanajemenproperti.ui.properti.view.OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ItemDetailProperti(
    modifier: Modifier = Modifier,
    jenisList: List<JenisProperti>,
    pemilikList: List<Pemilik>,
    manajerList: List<ManajerProperti>,
    properti: Properti
) {
    val formattedHarga = NumberFormat.getNumberInstance(Locale("id", "ID")).format(properti.harga)

    val pemilikMap = pemilikList.associate { it.idPemilik to it.namaPemilik }
    val pemilik = pemilikMap[properti.idPemilik] ?: ""

    val jenisMap = jenisList.associate { it.idJenis to it.namaJenis }
    val jenis = jenisMap[properti.idJenis] ?: ""

    val manajerMap = manajerList.associate { it.idManajer to it.namaManajer }
    val manajer = manajerMap[properti.idManajer] ?: ""

    Card (
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailProperti(judul = "ID Properti", isinya = properti.idProperti.toString())
            ComponentDetailProperti(judul = "Nama Properti", isinya = properti.namaProperti)
            ComponentDetailProperti(judul = "Deskripsi Properti", isinya = properti.deskripsiProperti)
            ComponentDetailProperti(judul = "Lokasi", isinya = properti.lokasi)
            ComponentDetailProperti(judul = "Harga", isinya = "Rp $formattedHarga")
            ComponentDetailProperti(judul = "Status Properti", isinya = properti.statusProperti)
            ComponentDetailProperti(judul = "Jenis Properti", isinya = jenis)
            ComponentDetailProperti(judul = "Pemilik Properti", isinya = pemilik)
            ComponentDetailProperti(judul = "Manajer Properti", isinya = manajer)
        }
    }
}

@Composable
fun ComponentDetailProperti(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
){
    Column (
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}