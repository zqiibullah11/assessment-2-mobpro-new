package org.d3if3053.tukarin.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3053.tukarin.database.DataKonversiDao
import org.d3if3053.tukarin.ui.screen.DetailViewModel
import org.d3if3053.tukarin.ui.screen.MainViewModel

class ViewModelFactory(
    private val dao: DataKonversiDao
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        }  else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}