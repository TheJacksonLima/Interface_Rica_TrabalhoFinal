package br.edu.utfpr.trabalhofinal.ui.utils.composables

import androidx.compose.material3.*
import androidx.compose.runtime.*
import java.sql.Date
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModalInput(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val selectedDateMillis = datePickerState.selectedDateMillis
                val formattedDate = selectedDateMillis?.let {
                    val sdf = SimpleDateFormat("yyyy-MM-dd")
                    sdf.format(Date(it))
                }
                if (formattedDate != null) {
                    onDateSelected(formattedDate)

                }
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancela")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}