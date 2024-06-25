package org.d3if3053.tukarin.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3053.tukarin.database.DataKonversiDao
import org.d3if3053.tukarin.model.DataKonversi

class DetailViewModel(private val dao: DataKonversiDao) : ViewModel() {
    fun insert(judul: String, jumlah: String, mataUangTujuan: String, catatan: String) {
        val dataKonversi = DataKonversi(
            judul = judul,
            jumlah = jumlah,
            mataUangTujuan = mataUangTujuan,
            catatan = catatan
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(dataKonversi)
        }
    }

    suspend fun getNotes(id: Long): DataKonversi? {
        return dao.getDataById(id)
    }

    fun update(id: Long, judul: String, jumlah: String, mataUangTujuan: String, catatan: String) {
        val dataKonversi = DataKonversi(
            id = id,
            judul = judul,
            jumlah = jumlah,
            mataUangTujuan = mataUangTujuan,
            catatan = catatan
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(dataKonversi)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}