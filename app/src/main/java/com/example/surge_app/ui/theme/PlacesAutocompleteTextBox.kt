package com.example.surge_app.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import com.example.surge_app.data.Prediction


@Composable
fun AutocompleteTextField(predictions: List<Prediction>) {
    var searchText by remember { mutableStateOf(TextFieldValue()) }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Column {
        TextField(
            value = searchText,
            onValueChange = { newValue -> searchText = newValue },
        )


        if (isDropdownExpanded) {
            DropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false }
            ) {
                predictions.filter { it.description.contains(searchText.text, ignoreCase = true) }
                    .forEach { prediction ->
                        Text(
                            text = prediction.description,
                        )
                    }
            }
        }
    }
}

