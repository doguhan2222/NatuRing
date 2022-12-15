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

class AllRingtonesViewModel (var ringtonesRepository: AllRingtonesRepository): ViewModel(){




    var kitaplikSayfaDurum: MutableLiveData<String> = MutableLiveData<String>()

    var cikisButonDurum: MutableLiveData<String> = MutableLiveData<String>()

    //adapterden geliyor
    val listeTiklananMuzikId = MutableLiveData<String>()

    var ucretsizAllSayfaDurumu: MutableLiveData<String> = MutableLiveData<String>()
    var ucretliAllSayfaDurumu: MutableLiveData<String> = MutableLiveData<String>()

    var listeTiklananMuzikURLAnasayfa = MutableLiveData<String>()

    //indirmek için
    var listeTiklananMuzikURLUcretsizAnasayfaIndir = MutableLiveData<String>()


    val allDeveloper: MutableLiveData<MutableList<AllRingtonesResponseModel>>
        get() = ringtonesRepository.getAllRingtones()







    var mediaPlayer: MediaPlayer? = null

    fun kitaplikToolbarOnClick(){
        kitaplikSayfaDurum.postValue("1")
    }
    fun kitaplikToolbarOnClickReturn():LiveData<String>{

        return kitaplikSayfaDurum
    }

    fun exitToolbarOnClick(){
        cikisButonDurum.postValue("1")
    }
    fun exitToolbarOnClickReturn():LiveData<String>{

        return cikisButonDurum
    }

    fun returnRingPay():MutableLiveData<String>{
        return listeTiklananMuzikId
    }

    fun returnRings(): MutableLiveData<String> {
        return listeTiklananMuzikURLAnasayfa
    }

    fun returnRingsDowloandUrl(): MutableLiveData<String> {
        return listeTiklananMuzikURLUcretsizAnasayfaIndir
    }

    fun ucretliSayfaClick(){
        ucretliAllSayfaDurumu.postValue("1")
    }
    fun ucreliClickReturn():LiveData<String>{

        return ucretliAllSayfaDurumu
    }

    fun ucretsizSayfaClick(){
        ucretsizAllSayfaDurumu.postValue("1")
    }

    fun ucretsizClickReturn(): LiveData<String> {
        return ucretsizAllSayfaDurumu
    }

    fun onClickIndir(context: Context){
        val downloadmanager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri: Uri = Uri.parse(listeTiklananMuzikURLUcretsizAnasayfaIndir.value.toString())

        val request = DownloadManager.Request(uri)
        request.setTitle("Ringtone")
        request.setDescription("Downloading")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "ringtone.mp3");
        request.setVisibleInDownloadsUi(false)


        downloadmanager!!.enqueue(request)
    }

    var caliyor:Boolean = false

    fun onClick(){

       Log.e("aaa","müzik basladi")
        //mediaPlayer = MediaPlayer()

        if(caliyor ==true && mediaPlayer != null){
            //mediaPlayer = MediaPlayer()
            mediaPlayer!!.stop()

            // on below line we are resetting
            // our media player.
            mediaPlayer!!.reset()

            // on below line we are calling
            // release to release our media player.
            mediaPlayer!!.release()
            mediaPlayer =null


            try {
                if(!listeTiklananMuzikURLAnasayfa.equals("")){


                    mediaPlayer = MediaPlayer()
                    mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
                    // on below line we are setting audio
                    // source as audio url on below line.
                    mediaPlayer!!.setDataSource(listeTiklananMuzikURLAnasayfa.value.toString())

                    // on below line we are
                    // preparing our media player.
                    mediaPlayer!!.prepare()

                    // on below line we are
                    // starting our media player.
                    mediaPlayer!!.start()
                    caliyor = true

                }

            } catch (e: Exception) {

                // on below line we are handling our exception.
                e.printStackTrace()
            }

        }else{
            mediaPlayer = MediaPlayer()
            mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
            try {
                if(!listeTiklananMuzikURLAnasayfa.equals("")){
                    // on below line we are setting audio
                    // source as audio url on below line.
                    mediaPlayer!!.setDataSource(listeTiklananMuzikURLAnasayfa.value.toString())

                    // on below line we are
                    // preparing our media player.
                    mediaPlayer!!.prepare()

                    // on below line we are
                    // starting our media player.
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
        if(caliyor ==true && mediaPlayer != null){

            mediaPlayer!!.stop()

            // on below line we are resetting
            // our media player.
            mediaPlayer!!.reset()

            // on below line we are calling
            // release to release our media player.
            mediaPlayer!!.release()
            mediaPlayer = null
        }

    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val repository: AllRingtonesRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AllRingtonesViewModel(repository) as T
        }
    }
}