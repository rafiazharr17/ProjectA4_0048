package com.rafi.aplikasimanajemenproperti.ui.pemilik.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rafi.aplikasimanajemenproperti.model.Pemilik
import com.rafi.aplikasimanajemenproperti.ui.navigation.DestinasiNavigasi
import com.rafi.aplikasimanajemenproperti.ui.pemilik.viewmodel.DetailPemilikUiState

object DestinasiDetailPemilik : DestinasiNavigasi {
    override val route = "detail"
    override val titleRes = "Detail Pemilik"
    const val ID = "idPemilik"
    val routeWithArg = "$route/{$ID}"
}

@Composable
fun DetailPemilikStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailPemilikUiState: DetailPemilikUiState
) {
    when (detailPemilikUiState) {
        is DetailPemilikUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is DetailPemilikUiState.Success -> {
            if (detailPemilikUiState.pemilik.idPemilik.toString().isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailPemilik(
                    pemilik = detailPemilikUiState.pemilik,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }

        is DetailPemilikUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailPemilik(
    modifier: Modifier = Modifier,
    pemilik: Pemilik
) {
    Card (
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailPemilik(judul = "ID Pemilik", isinya = pemilik.idPemilik.toString())
            ComponentDetailPemilik(judul = "Nama Pemilik", isinya = pemilik.namaPemilik)
            ComponentDetailPemilik(judul = "Kontak Pemilik", isinya = pemilik.kontakPemilik)
        }
    }
}

@Composable
fun ComponentDetailPemilik(
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