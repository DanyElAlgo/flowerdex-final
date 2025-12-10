package com.example.flowerdexapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flowerdexapp.R
import com.example.flowerdexapp.data.Flor
import com.example.flowerdexapp.data.TipoColor
import com.example.flowerdexapp.data.TipoEstacion
import com.example.flowerdexapp.data.TipoExposicion

@Composable
fun HomePage(
    onRegisterClick: () -> Unit,
    onIndexClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            // Este debería ser el logo, sin embargo, dado que aún no fue diseñado, se añadirá solo un texto de prueba
            Text(text = "Flowerdex", style = MaterialTheme.typography.displayMedium)
        }

        Column(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            // Button 1: Registrar
            Button(
                onClick = { onRegisterClick() },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(60.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.photo_camera_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                    contentDescription = "Registrar",
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "Registrar", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Button 2: Enciclopedia
            Button(
                onClick = { onIndexClick() },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(60.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.menu_book_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                    contentDescription = "Enciclopedia",
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "Enciclopedia", fontSize = 18.sp)
            }
        }
        Box(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = "Version 1.0.0",
                modifier = Modifier.padding(bottom = 16.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview(){
    HomePage(onRegisterClick = {}, onIndexClick = {})
}