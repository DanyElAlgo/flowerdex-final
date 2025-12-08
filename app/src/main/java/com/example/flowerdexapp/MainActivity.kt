package com.example.flowerdexapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flowerdexapp.ui.theme.FlowerdexAppTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlowerdexAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SmallTopAppBarExample(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

//Método de guardado, se moverá a otros archivos más adelante
data class Flor(
    val id: Long = 0,
    val nombreCientifico: String,
    val nombreComun: String,
    val familia: String,

    // Requisitos Ambientales
    val exposicionSolar: TipoExposicion,
    val frecuenciaRiego: Int, // Días entre riegos
    val temperaturaMinima: Double,

    // Características Visuales
    val colores: List<String>,
    val esToxica: Boolean,

    // Datos del Usuario
    val fechaAvistamiento: Long? = null,
    val fotoUri: String? = null
)

enum class TipoExposicion {
    SOL_DIRECTO, SEMI_SOMBRA, SOMBRA_TOTAL
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallTopAppBarExample(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Enciclopedia",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        IndexPage(modifier = Modifier.padding(innerPadding))
    }
}

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
fun FlowerListItem(flower: Flor, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
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
fun FlowerBlockItem(flower: Flor, modifier: Modifier = Modifier) {
    // Aquí se definirá el diseño del bloque de cada flor
}

@Composable
fun IndexPage(modifier: Modifier = Modifier) {
    var isListView by remember { mutableStateOf(true) }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { /* TODO: Open Sort Dialog */ }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Sort,
                    contentDescription = "Sort",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Ordenar",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            IconButton(onClick = { isListView = !isListView }) {
                Icon(
                    imageVector = if (isListView) Icons.Default.GridView else Icons.Default.List,
                    contentDescription = "Switch View",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(10) { index ->
                if (isListView) {
                    FlowerListItem(
                        flower = Flor(
                            id = index.toLong(),
                            nombreCientifico = "Flor $index",
                            nombreComun = "Flor Común $index",
                            familia = "Familia $index",
                            exposicionSolar = TipoExposicion.SOL_DIRECTO,
                            frecuenciaRiego = 7,
                            temperaturaMinima = 15.0,
                            colores = listOf("Rojo", "Verde"),
                            esToxica = false
                        )
                    )
                } else {
                    // TODO: Implementar FlowerBlockItem
                    Text("Block View Item $index", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FlowerdexAppTheme {
        SmallTopAppBarExample()
    }
}