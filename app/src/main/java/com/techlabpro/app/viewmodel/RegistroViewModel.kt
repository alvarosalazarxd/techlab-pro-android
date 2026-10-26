package com.techlabpro.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techlabpro.app.data.local.EquipoEntity
import com.techlabpro.app.data.local.EquipoRepository
import com.techlabpro.app.domain.usecase.CalculadoraTarifasUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegistroViewModel(private val repository: EquipoRepository) : ViewModel() {

    // Expone la lista de equipos en tiempo real desde Room
    val listaEquipos: StateFlow<List<EquipoEntity>> = repository.todosLosEquipos
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    var codigo by mutableStateOf("")
        private set

    var marca by mutableStateOf("")
        private set

    var modelo by mutableStateOf("")
        private set

    // Estado para guardar la URI de la foto (Recurso Nativo)
    var imagenUri by mutableStateOf<String?>(null)
        private set

    var codigoError by mutableStateOf<String?>(null)
        private set

    var marcaError by mutableStateOf<String?>(null)
        private set

    var mensajeExito by mutableStateOf<String?>(null)
        private set

    fun onCodigoChanged(nuevoCodigo: String) {
        val codigoLimpio = nuevoCodigo.trim().replace("\\s+".toRegex(), "").uppercase()
        codigo = codigoLimpio

        codigoError = if (codigoLimpio.isEmpty() || CalculadoraTarifasUseCase.validarCodigo(codigoLimpio)) {
            null
        } else {
            "Formato inválido. Debe ser XX00XX (Ej: LB12PC)"
        }
    }

    fun onMarcaChanged(nuevaMarca: String) {
        marca = nuevaMarca
        marcaError = if (marca.isBlank()) "La marca no puede estar vacía" else null
    }

    fun onModeloChanged(nuevoModelo: String) {
        modelo = nuevoModelo
    }

    fun onImagenSeleccionada(uri: String?) {
        imagenUri = uri
    }

    fun esFormularioValido(): Boolean {
        val codigoLimpio = codigo.trim()
        return CalculadoraTarifasUseCase.validarCodigo(codigoLimpio) && marca.isNotBlank()
    }

    fun guardarEquipo() {
        if (!esFormularioValido()) return

        val codigoAInsertar = codigo.trim()
        val marcaAInsertar = marca.trim()
        val modeloAInsertar = modelo.trim().ifBlank { "N/A" }
        val fechaActual = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        viewModelScope.launch(Dispatchers.IO) {
            val nuevoEquipo = EquipoEntity(
                codigo = codigoAInsertar,
                marca = marcaAInsertar,
                modelo = modeloAInsertar,
                tipoUsuario = "ESTUDIANTE",
                fechaIngreso = fechaActual,
                imagenUri = imagenUri // Guardar URI del recurso nativo
            )
            repository.insertar(nuevoEquipo)

            launch(Dispatchers.Main) {
                mensajeExito = "¡Equipo $codigoAInsertar guardado correctamente en Room!"
                codigo = ""
                marca = ""
                modelo = ""
                imagenUri = null // Reiniciar la foto adjunta
                codigoError = null
                marcaError = null
            }
        }
    }
}