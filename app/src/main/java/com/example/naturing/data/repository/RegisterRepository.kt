package com.example.naturing.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.naturing.data.entities.RegisterResponseModel
import com.example.naturing.data.remote.ApiService
import kotlinx.coroutines.delay
import retrofit2.Response

class RegisterRepository(val apiService: ApiService) {
    val registerResultFromAPI: MutableLiveData<RegisterResponseModel> by lazy {
        MutableLiveData<RegisterResponseModel>()
    }
    var resultRegister: MutableLiveData<String> = MutableLiveData<String>()

    // Viewmodel içinden çağırılır.PArametre değerlerini ApiService içindeki
    // kayit  fonk. yollar ve Api'a istek atar.Dönen değeri Viewmodel'e return eder.
    suspend fun getRegister(name: String, surname: String, username: String, password: String, email: String,phone: String
    ): MutableLiveData<String> {
        var call2: Response<RegisterResponseModel> = apiService.register(name, surname, username, password, email,phone).execute()

        if (call2.isSuccessful) {
            registerResultFromAPI.postValue(call2.body())
            delay(1300)
            if (registerResultFromAPI.value!!.result.equals("KAYIT BASARILI")) {
                resultRegister.postValue("Tebrikler kayit oldunuz")
            } else {
                resultRegister.postValue("kayit olunamadı")
            }
        }
        return resultRegister
    }


}
