package com.techlabpro.app.data.local

import kotlinx.coroutines.flow.Flow

class EquipoRepository(private val equipoDao: EquipoDao) {

    val todosLosEquipos: Flow<List<EquipoEntity>> = equipoDao.obtenerTodosLosEquipos()

    suspend fun insertar(equipo: EquipoEntity) {
        equipoDao.insertarEquipo(equipo)
    }

    suspend fun eliminarPorCodigo(codigo: String) {
        equipoDao.eliminarEquipoPorCodigo(codigo)
    }
}