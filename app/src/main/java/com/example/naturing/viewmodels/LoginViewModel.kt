package com.example.naturing.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.naturing.data.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel (var loginRepo: LoginRepository): ViewModel(){

    var Username: MutableLiveData<String> = MutableLiveData<String>()
    var Password: MutableLiveData<String> = MutableLiveData<String>()
    var result: MutableLiveData<String> = MutableLiveData<String>()
    var kayitSayfasi: MutableLiveData<String> = MutableLiveData<String>()

    fun onClick(){

        viewModelScope.launch(Dispatchers.IO) {
            println("thread running on [${Thread.currentThread().name}]")
            // repo içindeki fonk. çağırarak giriş için xml'den aldığı bilgileri
            // API'ya yolluyor.Return değerini de result'a atıyor.
            Log.e("deneme2",Username.value.toString() + Password.value.toString())
            result.postValue(loginRepo.login(Username.value.toString(),Password.value.toString()).value)
            Log.e("deneme3",result.value.toString())
        }

    }
    fun onClick2(){
        kayitSayfasi.postValue("1")


    }
    fun sayfaDurum():LiveData<String>{
        return kayitSayfasi
    }
    fun finalResult():LiveData<String>{
        return result
    }


    @Suppress("UNCHECKED_CAST")
    class Factory(private val repository: LoginRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(repository) as T
        }
    }
}