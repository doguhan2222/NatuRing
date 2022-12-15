package com.example.naturing.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.naturing.data.entities.AllRingtonesResponseModel
import com.example.naturing.data.entities.LibraryResponseModel
import com.example.naturing.data.entities.PayResponseModel
import com.example.naturing.data.remote.ApiService
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LibraryRepository(val apiService: ApiService) {
    val libraryResultFromApi: MutableLiveData<MutableList<LibraryResponseModel>> by lazy {
        MutableLiveData<MutableList<LibraryResponseModel>>()
    }


    fun getLibraryList(userId: String): MutableLiveData<MutableList<LibraryResponseModel>> {
        apiService.kitaplik(userId).enqueue(object : Callback<MutableList<LibraryResponseModel>> {
            override fun onResponse(
                call: Call<MutableList<LibraryResponseModel>>,
                response: Response<MutableList<LibraryResponseModel>>
            ) {
                libraryResultFromApi.postValue(response.body())
            }

            override fun onFailure(call: Call<MutableList<LibraryResponseModel>>, t: Throwable) {
                Log.e("deneme", t.message.toString())
            }
        })
        return libraryResultFromApi
    }


}

