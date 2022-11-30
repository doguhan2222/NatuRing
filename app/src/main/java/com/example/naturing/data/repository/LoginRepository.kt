package com.example.naturing.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.naturing.data.entities.LoginResponseModel
import com.example.naturing.data.remote.ApiService
import kotlinx.coroutines.delay
import retrofit2.Response

class LoginRepository(val apiService: ApiService){
    val loginResultFromAPI: MutableLiveData<LoginResponseModel> by lazy {
        MutableLiveData<LoginResponseModel>()
    }
    var resultLogin: MutableLiveData<String> = MutableLiveData<String>()

    // Viewmodel içinden çağırılır.PArametre değerlerini ApiService içindeki
    // login fonk. yollar ve Api'a istek atar.Dönen değeri Viewmodel'e return eder.
    suspend fun login(username: String, password: String): MutableLiveData<String> {
        var call: Response<LoginResponseModel> = apiService.login(username, password).execute()
        if (call.isSuccessful) {
            loginResultFromAPI.postValue(call.body())
            delay(1700)
            if (loginResultFromAPI.value!!.result.equals("Giris basarili")) {
                resultLogin.postValue("Tebrikler")
            } else {
                resultLogin.postValue("olmadi")
            }
        }else{
            Log.e("deneme",call.message())
        }
        return resultLogin
    }
}