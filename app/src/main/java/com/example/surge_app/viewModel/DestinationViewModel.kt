package com.example.surge_app.viewModel

import android.telecom.Call
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surge_app.data.RetrofitClient
import com.example.surge_app.data.SearchRequest

import com.example.surge_app.network.PlacesNewApiService
import com.squareup.okhttp.ResponseBody
import kotlinx.coroutines.launch
import retrofit2.Response

sealed interface DestinationUiState{
    data class Success(val response: Response<ResponseBody>): DestinationUiState
    object Error: DestinationUiState
    object Loading: DestinationUiState
}

class DestinationViewModel: ViewModel(){
    //Mutable variable to keep the state of the coroutine that is fetching the destination
    var destinationUiState: DestinationUiState by mutableStateOf(DestinationUiState.Loading)
    var query by mutableStateOf("")
    private val retrofit = RetrofitClient.retrofit.create(PlacesNewApiService::class.java)
    private val _responseData = MutableLiveData<Response<ResponseBody>>()
    val responseData: LiveData<Response<ResponseBody>> = _responseData

    val searchRequest = SearchRequest(query = query)

    fun getDestination(){
        viewModelScope.launch {
            destinationUiState = DestinationUiState.Loading
             try {
                RetrofitClient.changeRetrofitUrl("https://places.googleapis.com/v1")
                 val response = retrofit.searchText(searchRequest.query)
                 _responseData.value = response
                    destinationUiState = DestinationUiState.Success(retrofit.searchText(searchRequest.query))
                 if (!response.isSuccessful) {
                     val errorBody = response.errorBody()?.string()
                     Log.d("My Tag", "Error Response: $errorBody")
                 } else {
                     val responseBody = response.body()?.string()
                     Log.d("My Tag", "Response Body: $responseBody")
                 }

            } catch (e: Throwable){
                Log.d("My Tag", "Error: ${e.message}")
            destinationUiState = DestinationUiState.Error
            }
        }
    }

}