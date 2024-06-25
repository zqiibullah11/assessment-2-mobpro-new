package org.d3if3053.tukarin.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dataKonversi")
data class DataKonversi (
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0L,
        val judul: String,
        val jumlah: String,
        val mataUangTujuan: String,
        val catatan: String
)