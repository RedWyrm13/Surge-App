package com.example.surge_app.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surge_app.data.GooglePlaces
import com.example.surge_app.data.RetrofitClient
import com.example.surge_app.network.PlacesNewApiService
import kotlinx.coroutines.launch

sealed interface DestinationUiState{
    data class Success(val googlePlaces: List<GooglePlaces>): DestinationUiState
    object Error: DestinationUiState
    object Loading: DestinationUiState
}

class DestinationViewModel: ViewModel(){
    //Mutable variable to keep the state of the coroutine that is fetching the destination
    var destinationUiState: DestinationUiState by mutableStateOf(DestinationUiState.Loading)
    var address: String by mutableStateOf("")
    private val retrofit = RetrofitClient.retrofit.create(PlacesNewApiService::class.java)

    fun getDestination(){
        viewModelScope.launch {
            destinationUiState = DestinationUiState.Loading
            destinationUiState = try {
                DestinationUiState.Success(retrofit.getDestination(address= address))
            } catch (e: Throwable){
                Log.d("My Tag", "Error: ${e.message}")
                DestinationUiState.Error
            }
        }
    }

}