package com.example.flowerdexapp.ui

import android.widget.GridLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.flowerdexapp.R
import com.example.flowerdexapp.data.Flor


@Composable
fun ImageExample(
    modifier: Modifier = Modifier,
    sizeDp: Int = 200,
    borderWidthDp: Int = 2
) {
    val shape = RoundedCornerShape(12.dp)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(sizeDp.dp)
                .clip(shape)
        ) {
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = "Imagen",
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .border(borderWidthDp.dp, Color.Black, shape)
            )
        }
    }
}

@Composable
fun FlowerListItem(
    flower: Flor,
    onClick: () -> Unit,
    modifier: Modifier = Modifier) {
    Column(modifier = modifier.clickable{onClick()}) {
        // datos de la flor
        Row(modifier = Modifier.padding(16.dp)) {
            ImageExample(modifier = Modifier.width(56.dp))
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
                    text = "Fecha obtención: ${flower.fechaAvistamiento ?: "Desconocida"}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        // linea divisoria
        HorizontalDivider(thickness = 2.dp)
    }
}

@Composable
fun FlowerBlockItem(flower: Flor,
    onClick:() -> Unit,
    modifier: Modifier = Modifier
) {
    Column(){
        ImageExample(modifier = Modifier.width(120.dp))
        Text(
            text = flower.nombreComun,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = flower.nombreCientifico,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}