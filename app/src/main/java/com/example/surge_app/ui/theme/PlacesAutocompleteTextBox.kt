package com.example.surge_app.ui.theme

import android.location.Location
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import com.example.surge_app.data.AutocompleteResponse
import com.example.surge_app.viewModel.DestinationViewModel


@Composable
fun AutocompleteTextView(destinationViewModel: DestinationViewModel,
                          userLocation: Location
) {
    Log.d("My Tag", "11")
    var searchText by remember { mutableStateOf("") }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    Log.d("My Tag", "11")

    Column {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Go
            ),
            keyboardActions = KeyboardActions(onGo = {
                destinationViewModel.getDestination(searchText, userLocation)
            }),

        )
        Log.d("My Tag", "12")
        destinationViewModel.getPredictions(searchText)
        Log.d("My Tag", "13")

        if (isDropdownExpanded) {
            DropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false }
            ) {
                Log.d("My Tag", "14")
                destinationViewModel.predictions.value.filter { prediction ->
                    prediction.description.contains(
                        searchText,
                        ignoreCase = true
                    )
                }.forEach { prediction ->
                    Log.d("My Tag", "15")
                    Text(
                        text = prediction.description,
                        modifier = Modifier.clickable {
                            searchText = prediction.description
                            isDropdownExpanded = false
                            destinationViewModel.getDestination(
                                prediction.description,
                                userLocation
                            )
                        }
                    )
                    Log.d("My Tag", "16")
            }
        }
    }
    }
}

