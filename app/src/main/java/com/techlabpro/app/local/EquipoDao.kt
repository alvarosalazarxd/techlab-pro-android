package com.techlabpro.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EquipoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEquipo(equipo: EquipoEntity)

    @Query("SELECT * FROM equipos")
    fun obtenerTodosLosEquipos(): Flow<List<EquipoEntity>>

    @Query("DELETE FROM equipos WHERE codigo = :codigo")
    suspend fun eliminarEquipoPorCodigo(codigo: String)
}