package com.techlabpro.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.techlabpro.app.data.local.AppDatabase
import com.techlabpro.app.data.local.EquipoRepository
import com.techlabpro.app.ui.screens.RegistroScreen
import com.techlabpro.app.ui.theme.screens.ListaEquiposScreen
import com.techlabpro.app.viewmodel.RegistroViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = AppDatabase.getDatabase(applicationContext)
        val repository = EquipoRepository(db.equipoDao())

        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return RegistroViewModel(repository) as T
            }
        }
        val viewModel = ViewModelProvider(this, factory)[RegistroViewModel::class.java]

        setContent {
            var pantallaActual by remember { mutableStateOf("lista") }

            if (pantallaActual == "lista") {
                ListaEquiposScreen(
                    viewModel = viewModel,
                    onNavegarARegistro = { pantallaActual = "registro" }
                )
            } else {
                RegistroScreen(
                    viewModel = viewModel,
                    onNavegarALista = { pantallaActual = "lista" }
                )
            }
        }
    }
}