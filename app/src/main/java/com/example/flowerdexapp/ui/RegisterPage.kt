package com.example.flowerdexapp.ui

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.flowerdexapp.R
import com.example.flowerdexapp.utils.ImageUtils

@Composable
fun RegisterPage(
    viewModel: FlowerViewModel,
    onBackClick: () -> Unit,
    onScanSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scanState by viewModel.scanState.collectAsState()

    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }
    var imageUri by remember { mutableStateOf(viewModel.currentPhotoUri) }
    var showCameraPermissionDeniedDialog by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        uri?.let {
            imageUri = it
            viewModel.onPhotoSelected(it)
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempCameraUri != null) {
            imageUri = tempCameraUri
            viewModel.onPhotoSelected(tempCameraUri!!)
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            tempCameraUri = ImageUtils.createTempPictureUri(context)
            cameraLauncher.launch(tempCameraUri!!)
        } else {
            showCameraPermissionDeniedDialog = true
        }
    }

    LaunchedEffect(scanState) {
        when (scanState) {
            is ScanUiState.Success -> {
                viewModel.resetScanState()
                onScanSuccess()
            }
            is ScanUiState.Error -> {
                Toast.makeText(context, (scanState as ScanUiState.Error).mensaje, Toast.LENGTH_LONG)
                    .show()
                viewModel.resetScanState()
            }

            else -> {}
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ImageElement(
                imageUri = imageUri,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )

            Spacer(modifier = Modifier.size(16.dp))

            val isEnabled = scanState !is ScanUiState.Loading

            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 32.dp)
                    .fillMaxWidth()
            ) {
                ButtonElement(
                    text = "Cámara",
                    textStyle = MaterialTheme.typography.titleMedium,
                    onClick = {
                        val permission = android.Manifest.permission.CAMERA
                        val permissionCheck = androidx.core.content.ContextCompat.checkSelfPermission(
                            context,
                            permission
                        )
                        if (permissionCheck == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                            tempCameraUri = ImageUtils.createTempPictureUri(context)
                            cameraLauncher.launch(tempCameraUri!!)
                        } else {
                            cameraPermissionLauncher.launch(permission)
                        }
                    },
                    enabled = isEnabled,
                    icon = R.drawable.add_a_photo,
                    iconSize = 24,
                    modifier = Modifier.fillMaxWidth()
                )
                if (showCameraPermissionDeniedDialog) {
                    androidx.compose.material3.AlertDialog(
                        onDismissRequest = { showCameraPermissionDeniedDialog = false },
                        confirmButton = {
                            androidx.compose.material3.TextButton(onClick = { showCameraPermissionDeniedDialog = false }) {
                                Text("OK")
                            }
                        },
                        title = { Text("Permiso requerido") },
                        text = { Text("La aplicación necesita acceso a la cámara para tomar fotos de las flores.") }
                    )
                }
                ButtonElement(
                    text = "Galería",
                    textStyle = MaterialTheme.typography.titleMedium,
                    onClick = {
                        galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
                    enabled = isEnabled,
                    icon = R.drawable.image_arrow_up,
                    iconSize = 24,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            ButtonElement(
                text = "Escanear",
                textStyle = MaterialTheme.typography.titleMedium,
                onClick = { viewModel.escanearFlor() },
                enabled = isEnabled && imageUri != null,
                icon = R.drawable.image_search,
                iconSize = 24,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (scanState is ScanUiState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surface,
                    shadowElevation = 8.dp,
                    tonalElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Consultando a Gemini AI...",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}