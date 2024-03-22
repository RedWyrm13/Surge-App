
package com.example.surge_app.viewModel

import android.location.Location
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surge_app.data.ApiKey
import com.example.surge_app.data.AutocompleteResponse
import com.example.surge_app.data.GeocodingResponse
import com.example.surge_app.data.Prediction
import com.example.surge_app.data.RetrofitClient
import com.example.surge_app.data.RouteResponse
import com.example.surge_app.data.convertFromJsonStringToRoutesResponse
import com.example.surge_app.network.GeocodingApiService
import com.example.surge_app.network.PlacesApiService
import com.example.surge_app.network.routesPostRequest
import kotlinx.coroutines.launch
import androidx.compose.runtime.State

sealed interface DestinationUiState{
    data class Success(val routeResponse: RouteResponse?): DestinationUiState
    object Error: DestinationUiState
    object Loading: DestinationUiState
}
sealed interface AutocompleteUiState{
    data class  Success( val autocompleteResponse: AutocompleteResponse?): AutocompleteUiState
    object Error: AutocompleteUiState
    object Loading: AutocompleteUiState
}

class DestinationViewModel: ViewModel(){
    //Mutable variable to keep the state of the coroutine that is fetching the destination
    var destinationUiState: DestinationUiState by mutableStateOf(DestinationUiState.Loading)
    var encodedPolyline: String? = null
    var autocompleteUiState: AutocompleteUiState by mutableStateOf(AutocompleteUiState.Loading)
    private val geocodingApiService = RetrofitClient.retrofit.create(GeocodingApiService::class.java)
    private val placesApiService = RetrofitClient.retrofit.create(PlacesApiService::class.java)
    private val _predictions = mutableStateOf<List<Prediction>>(emptyList())
    val predictions: State<List<Prediction>> = _predictions

    fun getPredictions(query: String){
        viewModelScope.launch {
            try {
                autocompleteUiState = AutocompleteUiState.Success(placesApiService.getPlacesAutoComplete(query, ApiKey.apiKey))
            }
            catch (e: Throwable)
            {
                autocompleteUiState = AutocompleteUiState.Error
            }
        }
    }


    fun getDestination(query: String, userLocation: Location?){

        var coordinatesOfDestination: GeocodingResponse
        viewModelScope.launch {
            destinationUiState = DestinationUiState.Loading
            try {
                 coordinatesOfDestination = geocodingApiService.getCoordinates(query, ApiKey.apiKey)
                val routesResponse = convertFromJsonStringToRoutesResponse(routesPostRequest(coordinatesOfDestination, userLocation))
                destinationUiState = DestinationUiState.Success(routesResponse)
                encodedPolyline = routesResponse.routes[0].polyline.encodedPolyline
            }
            catch (e: Throwable){
                destinationUiState = DestinationUiState.Error

            }

        }

    }

}
