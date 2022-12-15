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
import com.example.naturing.data.entities.LibraryResponseModel
import com.example.naturing.data.repository.AllRingtonesRepository
import com.example.naturing.data.repository.LibraryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LibraryViewModel (var libraryRepository: LibraryRepository): ViewModel(){

    var listeTiklananMuzikURLKutuphaneDinle = MutableLiveData<String>()

    //indirmek için
    var listeTiklananMuzikURLKutuphaneIndir = MutableLiveData<String>()


    var mediaPlayer: MediaPlayer? = null


    val allLibrarySongs: MutableLiveData<MutableList<LibraryResponseModel>>
        get() = libraryRepository.getLibraryList(Ids.user_id)


    fun returnRingsListenUrlLibrary(): MutableLiveData<String> {
        return listeTiklananMuzikURLKutuphaneDinle
    }

    fun returnRingsDowloandUrlLibrary(): MutableLiveData<String> {
        return listeTiklananMuzikURLKutuphaneIndir
    }
    fun onClickIndir(context: Context){
        val downloadmanager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri: Uri = Uri.parse(listeTiklananMuzikURLKutuphaneIndir.value.toString())

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

    fun onClickDinle(){

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
                if(!listeTiklananMuzikURLKutuphaneDinle.equals("")){


                    mediaPlayer = MediaPlayer()
                    mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
                    // on below line we are setting audio
                    // source as audio url on below line.
                    mediaPlayer!!.setDataSource(listeTiklananMuzikURLKutuphaneDinle.value.toString())

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
                if(!listeTiklananMuzikURLKutuphaneDinle.equals("")){
                    // on below line we are setting audio
                    // source as audio url on below line.
                    mediaPlayer!!.setDataSource(listeTiklananMuzikURLKutuphaneDinle.value.toString())

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
        if(caliyor ==true && mediaPlayer != null) {
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
    class Factory(private val repository: LibraryRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LibraryViewModel(repository) as T
        }
    }
}