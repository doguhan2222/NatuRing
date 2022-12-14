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


    val listeTiklananMuzikId = MutableLiveData<String>()
    //Adapterden geliyor
    var listeTiklananMuzikURLUcretliTamliste = MutableLiveData<String>()

    val allPremiumRingtones: MutableLiveData<MutableList<AllRingtonesResponseModel>>
        get() = ringtonesPremiumRingtonesRepository.getPremiumAllRingtones()

    var mediaPlayer: MediaPlayer? = null


    fun returnRings(): MutableLiveData<String> {
        return listeTiklananMuzikURLUcretliTamliste
    }
    fun returnRingPay():MutableLiveData<String>{
        return listeTiklananMuzikId
    }

    var caliyor:Boolean = false

    fun onClick(){

        Log.e("aaa","müzik basladi")
        //mediaPlayer = MediaPlayer()

        if(caliyor ==true && mediaPlayer != null){
            //mediaPlayer = MediaPlayer()
            mediaPlayer!!.stop()

            mediaPlayer!!.reset()

            mediaPlayer!!.release()
            mediaPlayer =null


            try {
                if(!listeTiklananMuzikURLUcretliTamliste.equals("")){


                    mediaPlayer = MediaPlayer()
                    mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)

                    mediaPlayer!!.setDataSource(listeTiklananMuzikURLUcretliTamliste.value.toString())

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
                if(!listeTiklananMuzikURLUcretliTamliste.equals("")){

                    mediaPlayer!!.setDataSource(listeTiklananMuzikURLUcretliTamliste.value.toString())

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
    class Factory(private val repository: PremiumRingtonesRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PremiumRingtonesViewModel(repository) as T
        }
    }
}