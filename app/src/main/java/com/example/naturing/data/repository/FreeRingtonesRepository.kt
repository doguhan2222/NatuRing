package com.example.naturing.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.naturing.data.entities.AllRingtonesResponseModel
import com.example.naturing.data.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FreeRingtonesRepository(val apiService: ApiService) {
    val freeRingtonesResultFromAPI: MutableLiveData<MutableList<AllRingtonesResponseModel>> by lazy {
        MutableLiveData<MutableList<AllRingtonesResponseModel>>()
    }
    //var result: MutableLiveData<String> = MutableLiveData<String>()

    // Viewmodel içinden çağırılır.PArametre değerlerini ApiService içindeki
    // login fonk. yollar ve Api'a istek atar.Dönen değeri Viewmodel'e return eder.

    fun getFreeAllRingtones(): MutableLiveData<MutableList<AllRingtonesResponseModel>> {
        apiService.allRingtones().enqueue(object :
            Callback<MutableList<AllRingtonesResponseModel>> {
            override fun onResponse(
                call: Call<MutableList<AllRingtonesResponseModel>>,
                response: Response<MutableList<AllRingtonesResponseModel>>
            ) {
                freeRingtonesResultFromAPI.postValue(response.body())
            }

            override fun onFailure(
                call: Call<MutableList<AllRingtonesResponseModel>>,
                t: Throwable
            ) {
                Log.e("deneme33", t.toString())
            }
        })
        return freeRingtonesResultFromAPI
    }
}