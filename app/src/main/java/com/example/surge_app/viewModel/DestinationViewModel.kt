
package com.example.surge_app.viewModel

import android.location.Location
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surge_app.data.ApiKey
import com.example.surge_app.data.GeocodingResponse
import com.example.surge_app.data.RetrofitClient
import com.example.surge_app.data.RouteResponse
import com.example.surge_app.data.convertFromJsonStringToRoutesResponse
import com.example.surge_app.network.GeocodingApiService
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
    private val geocodingApiService = RetrofitClient.retrofit.create(GeocodingApiService::class.java)


    fun getDestination(query: String, userLocation: Location?){

        var coordinatesOfDestination: GeocodingResponse
        viewModelScope.launch {
            destinationUiState = DestinationUiState.Loading
            try {
                 coordinatesOfDestination = geocodingApiService.getCoordinates(query, ApiKey.apiKey)
                val routesResponse = convertFromJsonStringToRoutesResponse(routesPostRequest(coordinatesOfDestination, userLocation))
                destinationUiState = DestinationUiState.Success(routesResponse)
                encodedPolyline = routesResponse.routes[0].polyline.encodedPolyline
                Log.d("My Tag", encodedPolyline!!)
            }
            catch (e: Throwable){
                destinationUiState = DestinationUiState.Error

                Log.d("My Tag", "The following error message is from the first try block of getDestination in DestinationViewModel ${e.message}")
            }
            Log.d("My Tag", destinationUiState.toString())

        }

    }

}
