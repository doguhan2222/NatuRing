package com.example.naturing.viewmodels

import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.naturing.data.entities.AllRingtonesResponseModel
import com.example.naturing.data.repository.FreeRingtonesRepository

class FreeRingtonesViewModel (var ringtonesFreeRepository: FreeRingtonesRepository): ViewModel(){


    //Adapterden geliyor
    //caldırmak için
    var listeTiklananMuzikURLUcretsizTamliste = MutableLiveData<String>()

    //indirmek icin

    //indirmek için
    var listeTiklananMuzikURLUcretsizAnasayfaIndir = MutableLiveData<String>()

    val allFreeRingtones: MutableLiveData<MutableList<AllRingtonesResponseModel>>
        get() = ringtonesFreeRepository.getFreeAllRingtones()

    var mediaPlayer: MediaPlayer? = null


    fun returnRings(): MutableLiveData<String> {
        return listeTiklananMuzikURLUcretsizTamliste
    }

    fun returnRingsDowloandUrl(): MutableLiveData<String> {
        return listeTiklananMuzikURLUcretsizAnasayfaIndir
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


    // request.setDestinationUri(Uri.parse("file://" + folderName.toString() + "/myfile.mp3"))
    var caliyor:Boolean = false

    fun onClick(){

        Log.e("aaa","müzik basladi")

        if(caliyor ==true && mediaPlayer != null){
            mediaPlayer!!.stop()


            mediaPlayer!!.reset()


            mediaPlayer!!.release()
            mediaPlayer =null


            try {
                if(!listeTiklananMuzikURLUcretsizTamliste.equals("")){


                    mediaPlayer = MediaPlayer()
                    mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)

                    mediaPlayer!!.setDataSource(listeTiklananMuzikURLUcretsizTamliste.value.toString())


                    mediaPlayer!!.prepare()


                    mediaPlayer!!.start()
                    caliyor = true

                }

            } catch (e: Exception) {


                e.printStackTrace()
            }

        }else{
            mediaPlayer = MediaPlayer()
            mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
            try {
                if(!listeTiklananMuzikURLUcretsizTamliste.equals("")){

                    mediaPlayer!!.setDataSource(listeTiklananMuzikURLUcretsizTamliste.value.toString())


                    mediaPlayer!!.prepare()


                    mediaPlayer!!.start()

                    caliyor = true
                }

            } catch (e: Exception) {


                e.printStackTrace()
            }
        }

    }
    fun stopPlaySong(){
        if(caliyor ==true && mediaPlayer != null){
            mediaPlayer!!.stop()

            mediaPlayer!!.reset()


            mediaPlayer!!.release()
            mediaPlayer = null
        }

    }


    @Suppress("UNCHECKED_CAST")
    class Factory(private val repository: FreeRingtonesRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FreeRingtonesViewModel(repository) as T
        }
    }
}