package com.techlabpro.app.data.model

import java.time.LocalDateTime

enum class TipoUsuario { ESTUDIANTE, MENSUAL, DOCENTE }

data class Equipo(
    val codigo: String,
    val marca: String,
    val modelo: String,
    val tipo: TipoUsuario
)

sealed class EquipoReg {
    abstract val equipo: Equipo
    abstract val fechaIngreso: String

    data class Laptop(
        override val equipo: Equipo,
        override val fechaIngreso: String = LocalDateTime.now().toString()
    ) : EquipoReg()

    data class Desktop(
        override val equipo: Equipo,
        override val fechaIngreso: String = LocalDateTime.now().toString()
    ) : EquipoReg()

    data class EstacionGrafica(
        override val equipo: Equipo,
        val conGpuDedicada: Boolean,
        override val fechaIngreso: String = LocalDateTime.now().toString()
    ) : EquipoReg()
}

sealed class EstadoPuesto {
    object Disponible : EstadoPuesto()
    data class EnUso(val equipoReg: EquipoReg) : EstadoPuesto()
    data class EnProceso(val motivo: String) : EstadoPuesto()
    data class FueraDeServicio(val motivo: String) : EstadoPuesto()
}

data class Puesto(
    val numero: Int,
    var estado: EstadoPuesto = EstadoPuesto.Disponible
)