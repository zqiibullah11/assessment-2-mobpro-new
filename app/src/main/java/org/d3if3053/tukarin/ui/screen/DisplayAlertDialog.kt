package org.d3if3053.tukarin.ui.screen

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.d3if3053.tukarin.R
import org.d3if3053.tukarin.ui.theme.CustomGrayCard
import org.d3if3053.tukarin.ui.theme.CustomOrange
import org.d3if3053.tukarin.ui.theme.TukarInTheme

@Composable
fun DisplayAlertDialog(
    openDialog: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            containerColor = Color.White,
            textContentColor = CustomGrayCard,
            text = { Text(text = stringResource(R.string.pesan_hapus)) },
            confirmButton = {
                TextButton(onClick = { onConfirmation() }) {
                    Text(text = stringResource(R.string.tombol_hapus), color = CustomOrange)
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismissRequest() }) {
                    Text(text = stringResource(R.string.tombol_batal), color = CustomOrange)
                }
            },
            onDismissRequest = { onDismissRequest() }
        )
    }
}
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DialogPreview() {
    TukarInTheme {
        DisplayAlertDialog(
            openDialog = true,
            onDismissRequest = {  },
            onConfirmation = { }
        )
    }
}