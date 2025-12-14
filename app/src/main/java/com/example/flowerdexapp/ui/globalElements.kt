package com.example.flowerdexapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.flowerdexapp.R

@Composable
fun ButtonElement(
    text: String,
    textStyle: TextStyle,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: Int? = null,
    spacing: Int = 12,
    fontSize: Int? = null,
    iconSize: Int = 24
    ){
    Button(onClick = onClick, modifier = modifier, enabled = enabled) {
        if(icon != null){
            Image(
                painter = painterResource(id = icon),
                contentDescription = "Icono de botón",
                modifier = Modifier.size(iconSize.dp)
            )
            Spacer(modifier = Modifier.size(spacing.dp))
        }
        Text(
            text = text,
            style = textStyle,
            fontSize = fontSize?.sp ?: textStyle.fontSize
        )
    }
}

@Composable
fun SmallInfoElement(
    title: String,
    info: String,
    dangerous: Boolean = false,
    hyperlink: String? = null,
){
    val uriHandler = LocalUriHandler.current
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge
        )
        Row{
            Text(
                text = "$info ",
                style = MaterialTheme.typography.bodyMedium,
                color = if(dangerous) Color.Red else MaterialTheme.colorScheme.onBackground
            )
            if(hyperlink != null){
                Text(
                    text = "Más información.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable{
                        uriHandler.openUri(hyperlink)
                    }
                )
            }
        }
    }
}

@Composable
fun ImageElement(
    imageUri: Any?,
    modifier: Modifier = Modifier,
    sizeDp: Int? = null, // si el tamaño es null, se usará el del padre
    borderRadiusDp: Int = 12,
    borderThicknessDp: Int = 2
) {
    val shape = RoundedCornerShape(borderRadiusDp.dp)

    Box(
        modifier = modifier.then(
            if(sizeDp != null) Modifier.size(sizeDp.dp) else Modifier
        ).aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .clip(shape)
                .matchParentSize()
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
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize(),
                )
            }
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .border(borderThicknessDp.dp, Color.Black, shape)
            )
        }
    }
}
