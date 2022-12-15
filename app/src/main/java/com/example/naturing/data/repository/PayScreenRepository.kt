package com.example.naturing.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.naturing.data.entities.AllRingtonesResponseModel
import com.example.naturing.data.entities.Ids
import com.example.naturing.data.entities.LoginResponseModel
import com.example.naturing.data.entities.PayResponseModel
import com.example.naturing.data.remote.ApiService
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PayScreenRepository (val apiService: ApiService) {
    val payResultFromApi: MutableLiveData<PayResponseModel> by lazy {
        MutableLiveData<PayResponseModel>()
    }
    var resultPay: MutableLiveData<String> = MutableLiveData<String>()

    suspend fun getPay(userId: String, ringId: String): MutableLiveData<String> {
        var call: Response<PayResponseModel> = apiService.pay(userId, ringId).execute()
        if (call.isSuccessful) {
            payResultFromApi.postValue(call.body())
            delay(1700)

            Log.e("1515251",payResultFromApi.value.toString())
            if (payResultFromApi.value!!.result.equals("SATIN ALMA BASARILI")) {
                Log.e("222222",payResultFromApi.value.toString())
                resultPay.postValue("Tebrikler")

            } else {
                resultPay.postValue("olmadi")
            }
        }else{
            Log.e("deneme",call.message())
        }
        return resultPay
    }



}

