package com.example.naturing.viewmodels

import android.app.DownloadManager
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.lifecycle.*
import com.example.naturing.data.entities.AllRingtonesResponseModel
import com.example.naturing.data.entities.Ids
import com.example.naturing.data.repository.AllRingtonesRepository
import com.example.naturing.data.repository.PayScreenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PayScreenViewModel (var payScreenRepository: PayScreenRepository): ViewModel() {

    var resultFromPay: MutableLiveData<String> = MutableLiveData<String>()
    var payClick:MutableLiveData<String> = MutableLiveData<String>()

    fun payClickFonk(){
        payClick.postValue("1")
    }
    fun payClickFonkReturn():LiveData<String>{

        return payClick
    }


    fun payRingtones(userId:String,ringId:String){

        Log.e("000000",userId+" "+ringId)
        viewModelScope.launch(Dispatchers.IO) {
            resultFromPay.postValue(payScreenRepository.getPay(userId, ringId).toString())
        }
    }

    fun returnPayResult():LiveData<String>{
        return resultFromPay
    }


    @Suppress("UNCHECKED_CAST")
    class Factory(private val repository: PayScreenRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PayScreenViewModel(repository) as T
        }
    }
}
