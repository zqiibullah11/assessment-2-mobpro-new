package org.d3if3053.tukarin.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3053.tukarin.R
import org.d3if3053.tukarin.database.DataKonversiDb
import org.d3if3053.tukarin.ui.theme.CustomGrayCard
import org.d3if3053.tukarin.ui.theme.CustomOrange
import org.d3if3053.tukarin.ui.theme.TukarInTheme
import org.d3if3053.tukarin.util.ViewModelFactory

const val KEY_ID_Notes = "idNts"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val db = DataKonversiDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel:DetailViewModel = viewModel(factory = factory)

    var judul by remember { mutableStateOf("") }
    var jumlah by remember { mutableStateOf("") }
    var mataUangTujuan by remember { mutableStateOf("") }
    var catatan by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getNotes(id) ?: return@LaunchedEffect
        judul = data.judul
        jumlah = data.jumlah
        mataUangTujuan = data.mataUangTujuan
        catatan = data.catatan
    }

    Scaffold(
        topBar  = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = Color.White
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_catatan))
                    else
                        Text(text = stringResource(id = R.string.edit_riwayat_konversi))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = {
                        if (judul == "" || jumlah == "" || catatan == ""){
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }

                        if (id == null) {
                            viewModel.insert(judul, jumlah, mataUangTujuan, catatan)
                        } else {
                            viewModel.update(id, judul, jumlah, mataUangTujuan, catatan)
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = Color.White
                        )
                    }
                    if (id != null) {
                        DeleteAction { showDialog = true }
                        DisplayAlertDialog(
                            openDialog = showDialog,
                            onDismissRequest = { showDialog = false }) {
                            showDialog = false
                            viewModel.delete(id)
                            navController.popBackStack()

                        }
                    }
                }
            )
        }
    ) { padding ->
        FormNotes(
            judul = judul,
            onTitleChange = { judul = it },
            jumlah = jumlah,
            onSumChange = { jumlah = it },
            mataUangTujuan = mataUangTujuan,
            onCurrencyChange = { mataUangTujuan = it },
            catatan = catatan,
            onDescChange = { catatan = it },
            modifier = Modifier.padding(padding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormNotes(
    judul: String, onTitleChange: (String) -> Unit,
    jumlah: String, onSumChange: (String) -> Unit,
    mataUangTujuan: String, onCurrencyChange: (String) -> Unit,
    catatan: String, onDescChange: (String) -> Unit,
    modifier: Modifier
) {
    val radioOptions = listOf(
        stringResource(id = R.string.currency1),
        stringResource(id = R.string.currency2)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            shape = RoundedCornerShape(10.dp),
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = CustomGrayCard,
                disabledContainerColor = CustomGrayCard,
                focusedBorderColor = CustomOrange,
                unfocusedBorderColor = Color.White,
                disabledBorderColor = Color.White,
                focusedLabelColor = CustomOrange,
                unfocusedLabelColor = Color.White
            ),
            value = judul,
            onValueChange = { onTitleChange(it) },
            label = { Text(text = stringResource(R.string.judul)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            shape = RoundedCornerShape(10.dp),
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = CustomGrayCard,
                disabledContainerColor = CustomGrayCard,
                focusedBorderColor = CustomOrange,
                unfocusedBorderColor = Color.White,
                disabledBorderColor = Color.White,
                focusedLabelColor = CustomOrange,
                unfocusedLabelColor = Color.White
            ),
            value = jumlah,
            onValueChange = { onSumChange(it) },
            label = { Text(text = stringResource(R.string.jumlah)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .padding(top = 6.dp)
        ) {
            Text(
                text = stringResource(R.string.currency),
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
            radioOptions.forEach { text ->
                ClassOptions(
                    label = text,
                    isSelected = mataUangTujuan == text,
                    modifier = Modifier
                        .selectable(
                            selected = mataUangTujuan == text,
                            onClick = { onCurrencyChange(text) },
                            role = Role.RadioButton
                        )
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
        }
        OutlinedTextField(
            shape = RoundedCornerShape(10.dp),
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = CustomGrayCard,
                disabledContainerColor = CustomGrayCard,
                focusedBorderColor = CustomOrange,
                unfocusedBorderColor = Color.White,
                disabledBorderColor = Color.White,
                focusedLabelColor = CustomOrange,
                unfocusedLabelColor = Color.White
            ),
            value = catatan,
            onValueChange = { onDescChange(it) },
            label = { Text(text = stringResource(R.string.catatan)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 6.dp, bottom = 8.dp)
        )
    }
}
@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert ,
            contentDescription = stringResource(R.string.lainnya),
            tint = Color.White
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(R.string.hapus), color = Color.White)
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}

@Composable
fun ClassOptions(label: String, isSelected: Boolean, modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null,
            colors = RadioButtonDefaults.colors(
                selectedColor = CustomOrange,
                unselectedColor = Color.White
            )
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES,showBackground = true)
@Composable
fun DetailScreenPreview() {
    TukarInTheme {
        DetailScreen(rememberNavController())
    }
}