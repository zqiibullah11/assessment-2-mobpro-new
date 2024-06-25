package org.d3if3053.tukarin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if3053.tukarin.model.DataKonversi

@Database(entities = [DataKonversi::class], version = 1, exportSchema = false)
abstract class DataKonversiDb: RoomDatabase() {
    abstract val dao: DataKonversiDao

    companion object {
        @Volatile
        private var INSTANCE: DataKonversiDb? = null

        fun getInstance(context: Context): DataKonversiDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DataKonversiDb::class.java,
                        "dataKonversi.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}