package com.example.flowerdexapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import com.example.flowerdexapp.data.Flor
import com.example.flowerdexapp.utils.FlowerFilterState
import com.example.flowerdexapp.utils.SortOption
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun FlowerListItem(
    flower: Flor,
    onClick: () -> Unit,
    modifier: Modifier = Modifier) {
    Column(modifier = modifier.clickable{onClick()}) {
        Row(modifier = Modifier.padding(16.dp)) {
            ImageElement(
                imageUri = flower.fotoUri,
                modifier = Modifier.size(56.dp),
                borderRadiusDp = 8,
                borderThicknessDp = 1
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = flower.nombreComun,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = flower.nombreCientifico,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Fecha obtención: ${
                        flower.fechaAvistamiento?.let { millis ->
                            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(millis))
                        } ?: "Desconocida"
                    }",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        HorizontalDivider(thickness = 2.dp)
    }
}

@Composable
fun FlowerBlockItem(flower: Flor,
                    onClick:() -> Unit,
                    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        ImageElement(
            imageUri = flower.fotoUri,
            modifier = Modifier.fillMaxWidth(),
            borderRadiusDp = 12,
            borderThicknessDp = 2
        )
        Text(
            text = flower.nombreComun,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            overflow = Ellipsis
        )
        Text(
            text = flower.nombreCientifico,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = Ellipsis
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    currentFilter: FlowerFilterState,
    onFilterChange: (FlowerFilterState) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 32.dp)
        ) {
            Text("Ordenar y Filtrar", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))

            Text("Ordenar por:", style = MaterialTheme.typography.titleMedium)
            SortOption.entries.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onFilterChange(currentFilter.copy(sortBy = option)) }
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = (currentFilter.sortBy == option),
                        onClick = { onFilterChange(currentFilter.copy(sortBy = option)) }
                    )
                    Text(text = option.label)
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Text("Filtros:", style = MaterialTheme.typography.titleMedium)

            FilterSwitchRow("Ocultar Tóxicas", currentFilter.hideToxic) {
                onFilterChange(currentFilter.copy(hideToxic = it))
            }
            FilterSwitchRow("Ocultar 'Solo Sol'", currentFilter.hideSunOnly) {
                onFilterChange(currentFilter.copy(hideSunOnly = it))
            }
            FilterSwitchRow("Ocultar 'Solo Sombra'", currentFilter.hideShadeOnly) {
                onFilterChange(currentFilter.copy(hideShadeOnly = it))
            }
        }
    }
}

@Composable
fun FilterSwitchRow(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}