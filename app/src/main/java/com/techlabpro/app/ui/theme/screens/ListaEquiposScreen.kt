package com.techlabpro.app.ui.theme.screens

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.techlabpro.app.data.local.EquipoEntity
import com.techlabpro.app.viewmodel.RegistroViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaEquiposScreen(
    viewModel: RegistroViewModel,
    onNavegarARegistro: () -> Unit
) {
    val equipos by viewModel.listaEquipos.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Historial de Rendiciones") }) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNavegarARegistro
            ) {
                Text("Nueva Rendición")
            }
        }
    ) { paddingValues ->
        if (equipos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay gastos registrados aún.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(equipos) { equipo ->
                    TarjetaEquipo(equipo = equipo)
                }
            }
        }
    }
}

@Composable
fun TarjetaEquipo(equipo: EquipoEntity) {
    val context = LocalContext.current

    // Carga nativa de la imagen mediante Bitmap (sin dependencias externas)
    val bitmapState = remember(equipo.imagenUri) {
        derivedStateOf {
            equipo.imagenUri?.let { uriString ->
                try {
                    val uri = Uri.parse(uriString)
                    context.contentResolver.openInputStream(uri)?.use { stream ->
                        BitmapFactory.decodeStream(stream)
                    }
                } catch (e: Exception) {
                    null
                }
            }
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val bitmap = bitmapState.value
            if (bitmap != null) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Foto del comprobante",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Receipt,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = "Folio: ${equipo.codigo}", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "Categoría/Proveedor: ${equipo.marca}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Detalle: ${equipo.modelo}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Tipo Rendición: ${equipo.tipoUsuario}", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "Fecha Gasto: ${equipo.fechaIngreso}", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}