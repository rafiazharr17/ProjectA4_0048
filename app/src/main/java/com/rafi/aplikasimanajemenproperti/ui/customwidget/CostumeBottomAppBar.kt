package com.rafi.aplikasimanajemenproperti.ui.customwidget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.rafi.aplikasimanajemenproperti.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CostumeBottomAppBar(
    navigatePemilik: () -> Unit = {},
    navigateManajer: () -> Unit = {},
    navigateJenis: () -> Unit = {},
    navigateProperti: () -> Unit = {},
    modifier: Modifier = Modifier
){
    BottomAppBar(
        modifier = modifier
            .clip(RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp)),
        containerColor = Color.Cyan,
        contentColor = Color.Black
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = navigateProperti) {
                    Icon(
                        painter = painterResource(R.drawable.house),
                        contentDescription = "Properti"
                    )
                }
                Text(
                    text = "Properti"
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                IconButton(onClick = navigateJenis) {
                    Icon(
                        painter = painterResource(R.drawable.todolist),
                        contentDescription = "List jenis properti"
                    )
                }
                Text(
                    text = "Jenis Properti"
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                IconButton(onClick = navigateManajer) {
                    Icon(
                        painter = painterResource(R.drawable.manager),
                        contentDescription = "Manajer properti"
                    )
                }
                Text(
                    text = "Manajer"
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                IconButton(onClick = navigatePemilik) {
                    Icon(
                        painter = painterResource(R.drawable.user),
                        contentDescription = "Pemilik"
                    )
                }
                Text(
                    text = "Pemilik"
                )
            }
        }
    }
}