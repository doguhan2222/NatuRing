package com.example.naturing.viewmodels

import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.naturing.data.entities.AllRingtonesResponseModel
import com.example.naturing.data.repository.PremiumRingtonesRepository

class PremiumRingtonesViewModel (var ringtonesPremiumRingtonesRepository: PremiumRingtonesRepository): ViewModel(){


    //Adapterden geliyor
    var listeTiklananMuzikURLUcretliTamliste = MutableLiveData<String>()

    val allPremiumRingtones: MutableLiveData<MutableList<AllRingtonesResponseModel>>
        get() = ringtonesPremiumRingtonesRepository.getPremiumAllRingtones()

    var mediaPlayer: MediaPlayer? = null


    fun returnRings(): MutableLiveData<String> {
        return listeTiklananMuzikURLUcretliTamliste
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
                if(!listeTiklananMuzikURLUcretliTamliste.equals("")){


                    mediaPlayer = MediaPlayer()
                    mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
                    // on below line we are setting audio
                    // source as audio url on below line.
                    mediaPlayer!!.setDataSource(listeTiklananMuzikURLUcretliTamliste.value.toString())

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
                if(!listeTiklananMuzikURLUcretliTamliste.equals("")){
                    // on below line we are setting audio
                    // source as audio url on below line.
                    mediaPlayer!!.setDataSource(listeTiklananMuzikURLUcretliTamliste.value.toString())

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

    @Suppress("UNCHECKED_CAST")
    class Factory(private val repository: PremiumRingtonesRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PremiumRingtonesViewModel(repository) as T
        }
    }
}