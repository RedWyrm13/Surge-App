
package com.example.surge_app.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surge_app.data.RouteResponse
import com.example.surge_app.data.convertFromJsonStringToRoutesResponse
import com.example.surge_app.network.routesPostRequest
import kotlinx.coroutines.launch

sealed interface DestinationUiState{
    data class Success(val routeResponse: RouteResponse?): DestinationUiState
    object Error: DestinationUiState
    object Loading: DestinationUiState
}

class DestinationViewModel: ViewModel(){
    //Mutable variable to keep the state of the coroutine that is fetching the destination
    var destinationUiState: DestinationUiState by mutableStateOf(DestinationUiState.Loading)
    var encodedPolyline: String? = null

    fun getDestination(query: String, locationViewModel: LocationViewModel){
        viewModelScope.launch {
            destinationUiState = DestinationUiState.Loading
            try {
                val routesResponse = convertFromJsonStringToRoutesResponse(routesPostRequest())
                destinationUiState = DestinationUiState.Success(routesResponse)
                encodedPolyline = routesResponse.routes[0].polyline.encodedPolyline
            }
            catch (e: Throwable){
                Log.d("My Tag 1", "${e.message}")
            }
        }

    }

}
