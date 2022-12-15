package com.example.naturing.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.naturing.data.entities.AllRingtonesResponseModel
import com.example.naturing.data.entities.PayResponseModel
import com.example.naturing.data.entities.SearchScreenResponseModel
import com.example.naturing.data.remote.ApiService
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchScreenRepository (val apiService: ApiService) {

    val searchedAllRingtonesResultFromAPI: MutableLiveData<MutableList<SearchScreenResponseModel>> by lazy {
        MutableLiveData<MutableList<SearchScreenResponseModel>>()
    }

    // Viewmodel içinden çağırılır.PArametre değerlerini ApiService içindeki
    // login fonk. yollar ve Api'a istek atar.Dönen değeri Viewmodel'e return eder.

   /* fun getSearchedRingtones(searchedRingName: String): MutableLiveData<MutableList<SearchScreenResponseModel>> {
        apiService.searchRing(searchedRingName).enqueue(object :
            Callback<MutableList<SearchScreenResponseModel>> {
            override fun onResponse(
                call: Call<MutableList<SearchScreenResponseModel>>,
                response: Response<MutableList<SearchScreenResponseModel>>
            ) {
                Log.e("arama",response.body().toString())
                searchedAllRingtonesResultFromAPI.postValue(response.body())

            }

            override fun onFailure(
                call: Call<MutableList<SearchScreenResponseModel>>,
                t: Throwable
            ) {
                Log.e("deneme33", t.toString())
            }
        })
        return searchedAllRingtonesResultFromAPI
    }*/


    suspend fun getSearchedRingtones(searchedRingName: String): MutableLiveData<MutableList<SearchScreenResponseModel>> {
        var call: Response<MutableList<SearchScreenResponseModel>> = apiService.searchRing(searchedRingName).execute()
        if (call.isSuccessful) {
            searchedAllRingtonesResultFromAPI.postValue(call.body())
            delay(1300)
            Log.e("arama",call.body().toString())

        }else{
            Log.e("deneme",call.message())
        }
        return searchedAllRingtonesResultFromAPI
    }
}