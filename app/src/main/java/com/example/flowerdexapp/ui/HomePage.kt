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
            Image(
                painter = painterResource(id = R.drawable.flowerdex_icon),
                contentDescription = "Logo Flowerdex",
                modifier = Modifier.size(200.dp)
            )
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
                    painter = painterResource(id = R.drawable.photo_camera),
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
                    painter = painterResource(id = R.drawable.menu_book),
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