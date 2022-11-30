package com.example.naturing.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.naturing.data.entities.AllRingtonesResponseModel
import com.example.naturing.data.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllRingtonesRepository(val apiService: ApiService) {
    val allRingtonesResultFromAPI: MutableLiveData<MutableList<AllRingtonesResponseModel>> by lazy {
        MutableLiveData<MutableList<AllRingtonesResponseModel>> ()
    }
    //var result: MutableLiveData<String> = MutableLiveData<String>()

    // Viewmodel içinden çağırılır.PArametre değerlerini ApiService içindeki
    // login fonk. yollar ve Api'a istek atar.Dönen değeri Viewmodel'e return eder.

    fun getAllRingtones (): MutableLiveData<MutableList<AllRingtonesResponseModel>> {
            apiService.allRingtones().enqueue(object :
            Callback<MutableList<AllRingtonesResponseModel>>{
            override fun onResponse(
                call: Call<MutableList<AllRingtonesResponseModel>>,
                response: Response<MutableList<AllRingtonesResponseModel>>
            ) {

                Log.e("deneme33",response.body().toString())
               allRingtonesResultFromAPI.postValue(response.body())
            }

            override fun onFailure(
                call: Call<MutableList<AllRingtonesResponseModel>>,
                t: Throwable
            ) {
                Log.e("deneme33",t.toString())
            }
            })
        return allRingtonesResultFromAPI
    }





     /*fun getAllRingtones (): MutableLiveData<MutableList<AllRingtonesResponseModel>> {
        var call: Response<MutableList<AllRingtonesResponseModel>> = apiService.allRingtones().execute()
        if (call.isSuccessful) {
            liveData.postValue(call.body())
           // delay(800)
            Log.e("deneme22",liveData.value.toString())

        }else{
            Log.e("deneme",call.message())
        }
        return liveData
    }*/
}