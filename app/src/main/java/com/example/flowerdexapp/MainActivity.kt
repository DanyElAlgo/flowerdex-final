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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.style.TextAlign
import androidx.core.graphics.toRect
import java.time.Instant


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

//Métodó de guardado, se moverá a otros archivos más adelante
data class Flor(
    val id: Long = 0,
    val nombreCientifico: String,
    val nombreComun: String,
    val familia: String,
    val descripcion: String? = null,

    // Requisitos Ambientales
    val exposicionSolar: TipoExposicion,
    val frecuenciaRiego: Int, // Días entre riegos
    val estacionPreferida: TipoEstacion,
    val alcalinidadPreferida: String,

    // Otros datos de importancia
    val colores: List<TipoColor>,
    val esToxica: Boolean,

    // Datos del Usuario
    val fechaAvistamiento: Long? = null,
    val fotoUri: String? = null
)
enum class TipoExposicion(
    val descripcion: String
) {
    SOL_DIRECTO("Sol directo"),
    SEMI_SOMBRA("Semi sombra"),
    SOMBRA_TOTAL("Sombra total")
}

enum class TipoEstacion(
    val descripcion: String
) {
    PRIMAVERA("Primavera"),
    VERANO("Verano"),
    OTOÑO("Otoño"),
    INVIERNO("Invierno")
}

enum class TipoColor(
    val descripcion: String
) {
    ROJO("Rojo"),
    MARRON("Marron"),
    NARANJA("Naranja"),
    AMARILLO("Amarillo"),
    VERDE("Verde"),
    CELESTE("Celeste"),
    AZUL("Azul"),
    VIOLETA("Violeta"),
    ROSADO("Rosado"),
    GRIS("Gris"),
    BLANCO("Blanco"),
}

var flowerDatabase = listOf<Flor>()
//Base de datos vacía para pruebas
var flowerDatabaseEmpty = listOf<Flor>()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallTopAppBarExample(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
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
        IndexPage(modifier = Modifier.padding(top = innerPadding.calculateTopPadding()))
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
//    Validar que la base tenga datos, caso contrario, mostrar mensaje de "No hay datos"
    if (flowerDatabaseEmpty.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No hay flores registradas.\nEmpieza a capturar flores para\nllenar la enciclopedia.",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = "Imagen",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .drawWithCache {
                        val paint = Paint().apply {
                            blendMode = BlendMode.Multiply
                        }
                        onDrawWithContent {
                            drawIntoCanvas { canvas ->
                                canvas.saveLayer(size.toRect(), paint)
                                drawContent()
                                canvas.restore()
                            }
                        }
                    }
            )
            Button(onClick = { /*TODO*/ }) {
                Image(
                    painter = painterResource(id = R.drawable.photo_camera_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                    contentDescription = "Agregar flor",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Agregar flor")
            }
        }
        return
    }

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
        if (isListView) {
            LazyColumn {
                items(flowerDatabase.size) { index ->
                    FlowerListItem(flower = flowerDatabase[index])
                }
            }
        } else {
            // Implementar vista de bloques aquí
        }
    }
}

@Composable
fun FlowerPage(modifier: Modifier = Modifier) {
    // Página de detalles de la flor
    var flor = flowerDatabase[0] // Ejemplo: tomar la primera flor
    var scrollState = rememberScrollState()
    Column(modifier = modifier
        .fillMaxSize()
        .verticalScroll(scrollState)) {
        Image(
            painter = painterResource(id = R.drawable.placeholder),
            contentDescription = "Imagen de la flor",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.size(16.dp))
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Column(){
                Text(
                    text = flor.nombreComun,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = flor.nombreCientifico,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Fecha obtención: ${flor.fechaAvistamiento ?: "Desconocida"}",
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "Descripción general:",
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = flor.descripcion ?: "Descripción no disponible.",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "Información adicional:",
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column() {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(
                        text = "Familia:",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = flor.familia,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(
                        text = "Temporada:",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Desconocida", //TODO
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(
                        text = "Exposición al sol preferida:",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "flor.exposicionSolar", //TODO
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(
                        text = "Alcalinidad preferida:",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Desconocida", //TODO
                        style = MaterialTheme.typography.bodyMedium
                    )
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