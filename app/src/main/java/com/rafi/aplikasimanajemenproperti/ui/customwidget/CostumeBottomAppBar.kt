package com.rafi.aplikasimanajemenproperti.ui.customwidget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rafi.aplikasimanajemenproperti.R

@Composable
fun CostumeBottomAppBar(
    navigatePemilik: () -> Unit = {},
    navigateManajer: () -> Unit = {},
    navigateProperti: () -> Unit = {},
    activeMenu: String,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier
            .clip(RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp))
            .shadow(8.dp),
        containerColor = Color(0xFF1E88E5),
        contentColor = Color.White
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
                isActive = activeMenu == "Manajer",
                iconRes = R.drawable.manager,
                text = "Manajer",
                onClick = navigateManajer
            )
            IconWithText(
                isActive = activeMenu == "Pemilik",
                iconRes = R.drawable.user2,
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
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = if (isActive) Color.DarkGray else Color.White,
            modifier = Modifier.size(35.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = if (isActive) Color.DarkGray else Color.White,
            fontSize = 20.sp
        )
    }
}
