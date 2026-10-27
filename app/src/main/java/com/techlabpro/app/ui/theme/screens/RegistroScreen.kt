package com.techlabpro.app.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.techlabpro.app.viewmodel.RegistroViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    viewModel: RegistroViewModel,
    onNavegarALista: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    // Launcher para recurso nativo (Galería de imágenes)
    val launcherGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.onImagenSeleccionada(uri?.toString())
    }

    LaunchedEffect(viewModel.mensajeExito) {
        viewModel.mensajeExito?.let { mensaje ->
            snackbarHostState.showSnackbar(mensaje)
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("TeqGastos - TEQMED SPA") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavegarALista) {
                Icon(Icons.Default.Menu, contentDescription = "Ver lista de rendiciones")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = viewModel.codigo,
                onValueChange = { viewModel.onCodigoChanged(it) },
                label = { Text("Código / Folio Boleta (XX00XX)") },
                leadingIcon = { Icon(Icons.Default.Code, contentDescription = null) },
                trailingIcon = {
                    if (viewModel.codigoError != null) {
                        Icon(Icons.Default.Error, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                    }
                },
                isError = viewModel.codigoError != null,
                supportingText = {
                    viewModel.codigoError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.marca,
                onValueChange = { viewModel.onMarcaChanged(it) },
                label = { Text("Categoría / Proveedor (Ej: Peaje, Alimentación)") },
                leadingIcon = { Icon(Icons.Default.Devices, contentDescription = null) },
                isError = viewModel.marcaError != null,
                supportingText = {
                    viewModel.marcaError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Botón para activar el recurso nativo (Seleccionar Foto del Comprobante)
            OutlinedButton(
                onClick = { launcherGaleria.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Image, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (viewModel.imagenUri == null) "Adjuntar Foto de Boleta / Comprobante" else "Comprobante Seleccionado ✓")
            }

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedVisibility(
                visible = viewModel.esFormularioValido(),
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Button(
                    onClick = { viewModel.guardarEquipo() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrar Rendición de Gasto")
                }
            }
        }
    }
}