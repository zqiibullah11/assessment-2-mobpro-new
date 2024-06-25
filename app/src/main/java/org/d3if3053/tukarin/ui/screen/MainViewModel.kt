package org.d3if3053.tukarin.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if3053.tukarin.database.DataKonversiDao
import org.d3if3053.tukarin.model.DataKonversi

class MainViewModel(dao: DataKonversiDao): ViewModel() {
    val data: StateFlow<List<DataKonversi>> = dao.getDataKonversi().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}