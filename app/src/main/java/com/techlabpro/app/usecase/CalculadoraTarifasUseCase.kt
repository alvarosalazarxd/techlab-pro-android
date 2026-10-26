package com.techlabpro.app.domain.usecase

import com.techlabpro.app.data.model.EquipoReg
import com.techlabpro.app.data.model.TipoUsuario

object CalculadoraTarifasUseCase {

    fun validarCodigo(codigo: String): Boolean {
        // .trim() elimina espacios/saltos de línea y .uppercase() tolera minúsculas
        val codigoLimpio = codigo.trim().uppercase()
        val regex = Regex("^[A-Z]{2}[0-9]{2}[A-Z]{2}$")
        return regex.matches(codigoLimpio)
    }

    fun calcularMontoTotal(equipoReg: EquipoReg, minutosUso: Int): Double {
        if (minutosUso <= 0) throw IllegalArgumentException("El tiempo debe ser mayor a 0.")

        val horas = minutosUso / 60.0

        val costo = when (equipoReg) {
            is EquipoReg.Laptop -> {
                var tarifa = 2000.0 * horas
                if (equipoReg.equipo.tipo == TipoUsuario.MENSUAL) tarifa *= 0.80
                tarifa
            }
            is EquipoReg.Desktop -> {
                if (minutosUso < 30) 0.0 else 1500.0 * horas
            }
            is EquipoReg.EstacionGrafica -> {
                var tarifa = 4000.0 * horas
                if (equipoReg.conGpuDedicada) tarifa *= 1.30
                tarifa
            }
        }

        if (costo == 0.0) return 0.0

        var total = costo * 1.19
        if (equipoReg.equipo.tipo == TipoUsuario.DOCENTE) {
            total *= 0.50
        }

        return total
    }
}