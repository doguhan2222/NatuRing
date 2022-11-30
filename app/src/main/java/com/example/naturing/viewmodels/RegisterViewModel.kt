package com.example.naturing.viewmodels

import androidx.lifecycle.*
import com.example.naturing.data.repository.RegisterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel (var registerRepo: RegisterRepository) : ViewModel() {
    var kayitIsim: MutableLiveData<String> = MutableLiveData<String>()
    var kayitSoyisim: MutableLiveData<String> = MutableLiveData<String>()
    var kayitKullaniciAdi: MutableLiveData<String> = MutableLiveData<String>()
    var kayitSifre: MutableLiveData<String> = MutableLiveData<String>()
    var kayitMail: MutableLiveData<String> = MutableLiveData<String>()
    var kayitTelefon: MutableLiveData<String> = MutableLiveData<String>()
    var kayitResult: MutableLiveData<String> = MutableLiveData<String>()

    fun onClick(){

        viewModelScope.launch(Dispatchers.IO) {
            // repo içindeki fonk. çağırarak kayit için xml'den aldığı bilgileri
            // API'ya yolluyor.Return değerini de kayitResult'a atıyor.
            kayitResult.postValue(registerRepo.getRegister(
                kayitIsim.value.toString(),
                kayitSoyisim.value.toString(),
                kayitKullaniciAdi.value.toString(),
                kayitSifre.value.toString(),
                kayitMail.value.toString(),
                kayitTelefon.value.toString()).value)

        }

    }
    //Activity içinden observe edilen ve UI güncellenmesinde kullanılan fonk.
    fun finalRegisterResult(): LiveData<String> {
        return kayitResult
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val repository: RegisterRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RegisterViewModel(repository) as T
        }
    }
}