
package com.example.surge_app.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surge_app.data.GooglePlacesList
import com.example.surge_app.data.convertFromJsonStringToGooglePlacesList
import com.example.surge_app.network.placesPostRequest
import kotlinx.coroutines.launch

sealed interface DestinationUiState{
    data class Success(val googlePlacesList: GooglePlacesList?): DestinationUiState
    object Error: DestinationUiState
    object Loading: DestinationUiState
}

class DestinationViewModel: ViewModel(){
    //Mutable variable to keep the state of the coroutine that is fetching the destination
    var destinationUiState: DestinationUiState by mutableStateOf(DestinationUiState.Loading)

    fun getDestination(query: String, locationViewModel: LocationViewModel){
        viewModelScope.launch {
            destinationUiState = DestinationUiState.Loading
            try {
                //This will be changed to a routesPostRequest once I implement the routesPostRequestFunction
                destinationUiState = DestinationUiState.Success(
                    convertFromJsonStringToGooglePlacesList(placesPostRequest(query, locationViewModel)))

            }
            catch (e: Throwable){
                Log.d("My Tag 1", "${e.message}")
            }
        }

    }

}
