package com.example.naturing.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.naturing.data.entities.LoginResponseModel
import com.example.naturing.data.entities.Ids
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
            //loginResultFromAPI.value!!.result.equals("Giris basarili"

            if (loginResultFromAPI.value!!.result.subSequence(0,14).equals("Giris basarili")) {
                val id = loginResultFromAPI.value!!.result.subSequence(15, loginResultFromAPI.value!!.result.length)
                //kullanici id API resulttan alınıyor
                Ids.user_id = id.toString()
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