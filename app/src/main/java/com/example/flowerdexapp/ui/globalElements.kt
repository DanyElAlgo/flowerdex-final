package com.example.flowerdexapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import coil.compose.AsyncImage
import com.example.flowerdexapp.R

@Composable
fun ButtonElement(){

}

@Composable
fun SmallInfoElement(
    title: String,
    info: String,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = info,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun ImageElement(
    imageUri: String?,
    modifier: Modifier = Modifier,
    sizeDp: Int = 200,
    borderRadiusDp: Int = 12,
    borderWidthDp: Int = 2
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(sizeDp.dp)
                .clip(RoundedCornerShape(borderRadiusDp.dp))
        ) {
            if(imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Imagen",
                    placeholder = painterResource(id = R.drawable.placeholder),
                    error = painterResource(id = R.drawable.placeholder),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.placeholder),
                    contentDescription = "Placeholder",
                    modifier = Modifier.matchParentSize(),
                )
            }
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .border(borderWidthDp.dp,
                        Color.Black,
                        RoundedCornerShape(borderRadiusDp.dp))
            )
        }
    }
}