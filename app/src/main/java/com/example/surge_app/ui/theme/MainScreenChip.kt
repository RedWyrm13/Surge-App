package com.example.surge_app.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.surge_app.viewModel.DestinationViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenBottomBar(destinationViewModel: DestinationViewModel){
    ModalBottomSheet(
        onDismissRequest = { destinationViewModel.isSheetAvailable = false },
        modifier = Modifier.fillMaxSize()) {
        ChipInsideBottomBar(destinationViewModel)

    }
}

@Composable
fun ChipInsideBottomBar(destinationViewModel: DestinationViewModel){}
