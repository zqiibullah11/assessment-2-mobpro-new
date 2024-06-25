package org.d3if3053.tukarin.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if3053.tukarin.model.DataKonversi

@Dao
interface DataKonversiDao {
    @Insert
    suspend fun insert(notes: DataKonversi)

    @Update
    suspend fun update(notes: DataKonversi)

    @Query("SELECT * FROM dataKonversi ORDER BY judul ASC")
    fun getDataKonversi(): Flow<List<DataKonversi>>

    @Query("SELECT * FROM dataKonversi WHERE id = :id")
    suspend fun getDataById(id: Long) : DataKonversi?

    @Query("DELETE FROM dataKonversi WHERE id = :id")
    suspend fun deleteById(id: Long)
}