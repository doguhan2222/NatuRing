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
import com.example.naturing.data.entities.SearchScreenResponseModel
import com.example.naturing.data.repository.AllRingtonesRepository
import com.example.naturing.data.repository.PayScreenRepository
import com.example.naturing.data.repository.SearchScreenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchScreenViewModel(var searchScreenRepository: SearchScreenRepository) : ViewModel() {


    //val textBoxSearchRingName = MutableLiveData<String>()
    val allSearchedRings: MutableLiveData<MutableList<SearchScreenResponseModel>> =
        MutableLiveData()

    //adapterden geliyor
    val listeTiklananMuzikId = MutableLiveData<String>()

    var listeTiklananMuzikURLSearch = MutableLiveData<String>()

    //indirmek için
    var listeTiklananMuzikURLUcretsizSearchIndir = MutableLiveData<String>()


    fun getSearchRingFromRepo(searchRingName: String) {

        viewModelScope.launch(Dispatchers.IO) {
            println("thread running on [${Thread.currentThread().name}]")
            // repo içindeki fonk. çağırarak giriş için xml'den aldığı bilgileri
            // API'ya yolluyor.Return değerini de result'a atıyor.
            Log.e("arama4", allSearchedRings.value.toString())
            allSearchedRings.postValue(searchScreenRepository.getSearchedRingtones(searchRingName.toString()).value)
            Log.e("arama4", allSearchedRings.value.toString())

        }


        //TODO  ARMA SAYFASI SON DÜZENLEME YAP YENİ MÜZİK EKLE İNGİLİZCE YAP.

    }

    fun returnSearchRing(): MutableLiveData<MutableList<SearchScreenResponseModel>> {
        return allSearchedRings
    }


    /*val allSearchedRings: MutableLiveData<MutableList<SearchScreenResponseModel>>
        get() = searchScreenRepository.getSearchedRingtones(textBoxSearchRingName.value.toString())
*/
    var mediaPlayer: MediaPlayer? = null


    fun returnRingPay(): MutableLiveData<String> {
        return listeTiklananMuzikId
    }

    fun returnRings(): MutableLiveData<String> {
        return listeTiklananMuzikURLSearch
    }

    fun returnRingsDowloandUrl(): MutableLiveData<String> {
        return listeTiklananMuzikURLUcretsizSearchIndir
    }


    fun onClickIndir(context: Context) {
        val downloadmanager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri: Uri = Uri.parse(listeTiklananMuzikURLUcretsizSearchIndir.value.toString())

        val request = DownloadManager.Request(uri)
        request.setTitle("Ringtone")
        request.setDescription("Downloading")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "ringtone.mp3");
        request.setVisibleInDownloadsUi(false)


        downloadmanager!!.enqueue(request)
    }

    var caliyor: Boolean = false

    fun onClick() {

        Log.e("aaa", "müzik basladi")
        //mediaPlayer = MediaPlayer()

        if (caliyor == true && mediaPlayer != null) {
            //mediaPlayer = MediaPlayer()
            mediaPlayer!!.stop()


            mediaPlayer!!.reset()

            mediaPlayer!!.release()
            mediaPlayer = null


            try {
                if (!listeTiklananMuzikURLSearch.equals("")) {


                    mediaPlayer = MediaPlayer()
                    mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)

                    mediaPlayer!!.setDataSource(listeTiklananMuzikURLSearch.value.toString())

                    mediaPlayer!!.prepare()

                    mediaPlayer!!.start()
                    caliyor = true

                }

            } catch (e: Exception) {


                e.printStackTrace()
            }

        } else {
            mediaPlayer = MediaPlayer()
            mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
            try {
                if (!listeTiklananMuzikURLSearch.equals("")) {

                    mediaPlayer!!.setDataSource(listeTiklananMuzikURLSearch.value.toString())


                    mediaPlayer!!.prepare()


                    mediaPlayer!!.start()

                    caliyor = true
                }

            } catch (e: Exception) {

                // on below line we are handling our exception.
                e.printStackTrace()
            }
        }

    }
    fun stopPlaySong(){
        if(caliyor ==true && mediaPlayer != null) {
            mediaPlayer!!.stop()

            mediaPlayer!!.reset()


            mediaPlayer!!.release()
            mediaPlayer = null

        }

    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val repository: SearchScreenRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchScreenViewModel(repository) as T
        }
    }
}