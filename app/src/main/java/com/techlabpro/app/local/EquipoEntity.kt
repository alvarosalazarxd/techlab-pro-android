package com.techlabpro.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "equipos")
data class EquipoEntity(
    @PrimaryKey
    val codigo: String,
    val marca: String,
    val modelo: String,
    val tipoUsuario: String,
    val fechaIngreso: String,
    val imagenUri: String? = null // <-- Campo para el recurso nativo (foto)
)