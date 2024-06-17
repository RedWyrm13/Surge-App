package com.example.surge_app.viewModel

import android.location.Location
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surge_app.data.ApiKey
import com.example.surge_app.data.AutocompleteResponse
import com.example.surge_app.data.GeocodingResponse
import com.example.surge_app.data.RetrofitClient
import com.example.surge_app.data.convertFromJsonStringToRoutesResponse
import com.example.surge_app.data.repositories.RideRepoImpl
import com.example.surge_app.network.GeocodingApiService
import com.example.surge_app.network.PlacesApiService
import kotlinx.coroutines.launch


open class DestinationViewModel: ViewModel(){
    //Mutable variable to keep the state of the coroutine that is fetching the destination
    var encodedPolyline: String? = null
    private val placesApiService = RetrofitClient.retrofit.create(PlacesApiService::class.java)
    private val _predictions = mutableStateOf(AutocompleteResponse(predictions = emptyList(), status = "Initializer"))
    val predictions: State<AutocompleteResponse> = _predictions
    var isSheetAvailable by mutableStateOf(false)
    var distanceOfRoute by mutableIntStateOf(0)
    var durationOfRoute by mutableStateOf("0")
    val rideRepoImpl = RideRepoImpl()





    fun getPredictions(query: String) {

            viewModelScope.launch {
                try {
                     val newPredictions = placesApiService.getPlacesAutoComplete(query, ApiKey.apiKey)
                    _predictions.value = newPredictions
                    Log.d("My Tag Success 1: ", _predictions.toString())
                    Log.d("My Tag Success 2: ", predictions.toString())
                } catch (e: Throwable) {
                Log.d("My Tag Error: ", e.toString())
            }
        }
    }


    fun getDestination(query: String, userLocation: Location?){

        var coordinatesOfDestination: GeocodingResponse
        viewModelScope.launch {

            try {
                 coordinatesOfDestination = rideRepoImpl.getCoordinates(query, ApiKey.apiKey)
                val routesResponse = convertFromJsonStringToRoutesResponse(rideRepoImpl.routesPostRequest(coordinatesOfDestination, userLocation))
                encodedPolyline = routesResponse.routes[0].polyline.encodedPolyline
                distanceOfRoute = routesResponse.routes[0].distanceMeters
                durationOfRoute = routesResponse.routes[0].duration
                isSheetAvailable = true
            }
            catch (e: Throwable){
                Log.d("My Tag Error: ", e.toString())
            }

        }

    }

}
