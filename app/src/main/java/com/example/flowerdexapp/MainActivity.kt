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
import com.example.flowerdexapp.data.Flor
import androidx.compose.runtime.collectAsState
import com.example.flowerdexapp.data.AppDatabase
import com.example.flowerdexapp.ui.FlowerViewModel
import com.example.flowerdexapp.ui.FlowerViewModelFactory
import com.example.flowerdexapp.ui.IndexPage
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.flowerdexapp.ui.FlowerPage

sealed class Screen(val route: String){
    object Home : Screen("home")
    object Register : Screen("register")
    object Scan : Screen("scan")
    object Index : Screen("index")
    object Detail : Screen("detail/{flowerId}"){
        fun createRoute(flowerId: Long) = "detail/$flowerId"
    }
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)
        val viewModelFactory = FlowerViewModelFactory(database.florDao())

        setContent {
            FlowerdexAppTheme {
                val viewModel: FlowerViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
                    factory = viewModelFactory
                )
                MainNavigationWrapper(viewModel = viewModel)
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SmallTopAppBarExample(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigationWrapper(viewModel: FlowerViewModel) {
    val navController = rememberNavController()
    // Observamos la ruta actual para cambiar el título y mostrar/ocultar la flecha
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
                title = {
                    // Título dinámico
                    val titulo = if (currentRoute?.startsWith("detail") == true) "Detalle de Flor" else "Enciclopedia"
                    Text(titulo, maxLines = 1, overflow = TextOverflow.Ellipsis)
                },
                navigationIcon = {
                    // Solo mostramos la flecha si NO estamos en el Home
                    if (currentRoute != Screen.Home.route) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Atrás"
                            )
                        }
                    }
                }
            )
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // RUTA 1: Lista de Flores (Index)
            composable(Screen.Index.route) {
                IndexPage(
                    viewModel = viewModel,
                    onFlowerClick = { flowerId ->
                        // Navegar al detalle pasando el ID
                        navController.navigate(Screen.Detail.createRoute(flowerId))
                    }
                )
            }

            // RUTA 2: Detalle de Flor
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("flowerId") { type = NavType.LongType })
            ) { backStackEntry ->
                // Recuperamos el ID de los argumentos
                val flowerId = backStackEntry.arguments?.getLong("flowerId") ?: 0L

                FlowerPage(
                    flowerId = flowerId,
                    viewModel = viewModel
                )
            }
//            TODO: Agregar otras rutas según sea necesario
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallTopAppBarExample(
    viewModel: FlowerViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
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
        IndexPage(
            viewModel = viewModel,
            onFlowerClick = { flowerId ->
                // Navegar al detalle pasando el ID
                navController.navigate(Screen.Detail.createRoute(flowerId))
            },
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FlowerdexAppTheme {
        val viewModel: FlowerViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        SmallTopAppBarExample(viewModel = viewModel)
    }
}