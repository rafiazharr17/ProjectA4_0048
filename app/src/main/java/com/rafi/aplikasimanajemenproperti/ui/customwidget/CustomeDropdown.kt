package com.rafi.aplikasimanajemenproperti.ui.customwidget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> GeneralDropdown(
    label: String,
    itemList: List<T>,
    selectedId: Int?,
    onItemSelected: (Int) -> Unit,
    getItemLabel: (T) -> String,
    getItemId: (T) -> Int
) {
    val itemMap = itemList.associate { getItemLabel(it) to getItemId(it) }
    val options = itemMap.keys.toList()

    val expanded = remember { mutableStateOf(false) }
    val currentSelection = remember(selectedId, itemList) {
        mutableStateOf(
            itemMap.entries.find { it.value == selectedId }?.key ?: ""
        )
    }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = !expanded.value }
    ) {
        OutlinedTextField(
            value = currentSelection.value.ifEmpty { label },
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        currentSelection.value = selectionOption
                        expanded.value = false
                        onItemSelected(itemMap[selectionOption]!!)
                    }
                )
            }
        }
    }
}