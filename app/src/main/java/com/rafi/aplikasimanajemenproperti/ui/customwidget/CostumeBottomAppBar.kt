package com.rafi.aplikasimanajemenproperti.ui.customwidget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
    activeMenu: String,
    modifier: Modifier = Modifier
) {
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
            IconWithText(
                isActive = activeMenu == "Properti",
                iconRes = R.drawable.house,
                text = "Properti",
                onClick = navigateProperti
            )
            IconWithText(
                isActive = activeMenu == "Jenis",
                iconRes = R.drawable.todolist,
                text = "Jenis Properti",
                onClick = navigateJenis
            )
            IconWithText(
                isActive = activeMenu == "Manajer",
                iconRes = R.drawable.manager,
                text = "Manajer",
                onClick = navigateManajer
            )
            IconWithText(
                isActive = activeMenu == "Pemilik",
                iconRes = R.drawable.user,
                text = "Pemilik",
                onClick = navigatePemilik
            )
        }
    }
}

@Composable
fun IconWithText(
    isActive: Boolean,
    @DrawableRes iconRes: Int,
    text: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = text,
                tint = if (isActive) Color.Blue else Color.Black
            )
        }
        Text(
            text = text,
            color = if (isActive) Color.Blue else Color.Black
        )
    }
}