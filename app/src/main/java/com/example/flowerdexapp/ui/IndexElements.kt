package com.example.flowerdexapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import com.example.flowerdexapp.data.Flor
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