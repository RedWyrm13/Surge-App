package com.example.surge_app.ui.theme

import android.location.Location
import android.util.Log
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.surge_app.R
import com.example.surge_app.viewModel.DestinationViewModel


@Composable
fun AutocompleteTextView(destinationViewModel: DestinationViewModel,
                          userLocation: Location
) {
    // Variable for text field that will be sent to getDestination function
    var searchText by remember { mutableStateOf("") }

    //determines if the alert dialog is shown
    var showDialog by remember { mutableStateOf(false) }

    // This variables holds the correctly formatted address
    val predictions = destinationViewModel.predictions.value

    //This function is called when the go button on the keyboard is typed
    val onGoFunctionCall = {
            destinationViewModel.getPredictions(searchText)
            showDialog = true
    }

    //Text field for the top of the screen
        TextField(
            value = searchText,
            onValueChange = { newValue ->
                searchText = newValue
            },

            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Go
            ),
            keyboardActions = KeyboardActions(onGo = {
                //destinationViewModel.getDestination(searchText, userLocation)
                onGoFunctionCall()
            }),
        )
    //

    // Creates alert dialog if predictions exist based off user input
    if (showDialog && predictions.predictions.isNotEmpty()) {
        Log.d("My Tag New", predictions.toString())

        DestinationConfirmationAlertDialog(
            onDismissRequest = {showDialog = false},
            onConfirmRequest = {
                searchText = predictions.predictions[0].description.toString()
            destinationViewModel.getDestination(userLocation = userLocation, query = searchText)
                showDialog = false

            },
            dialogTitle = stringResource(id = R.string.address_confirmation),
            dialogText = "Did you mean ${predictions.predictions[0].description.toString()}?"
            )
    }
}

@Composable
fun DestinationConfirmationAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit,
    dialogTitle: String,
    dialogText: String,
) {
    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmRequest()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}
