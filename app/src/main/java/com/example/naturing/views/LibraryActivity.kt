package com.example.naturing.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.naturing.R
import com.example.naturing.adapters.FreeAllAdapter
import com.example.naturing.adapters.LibraryAdapter
import com.example.naturing.data.entities.AllRingtonesResponseModel
import com.example.naturing.data.entities.LibraryResponseModel
import com.example.naturing.data.remote.RetrofitFactory
import com.example.naturing.data.repository.FreeRingtonesRepository
import com.example.naturing.data.repository.LibraryRepository
import com.example.naturing.databinding.ActivityFreeAllRingtonesBinding
import com.example.naturing.databinding.ActivityLibraryBinding
import com.example.naturing.viewmodels.FreeRingtonesViewModel
import com.example.naturing.viewmodels.LibraryViewModel

class LibraryActivity : AppCompatActivity() {
    lateinit var binding: ActivityLibraryBinding

    val repository: LibraryRepository by lazy {
        LibraryRepository(RetrofitFactory.service)
    }

    private val viewModel: LibraryViewModel by lazy {
        val factory = LibraryViewModel.Factory(repository = repository)
        ViewModelProvider(this,factory).get(LibraryViewModel::class.java)
    }

    private var libraryAdapter: LibraryAdapter? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_library)
        setContentView(binding.root)
        binding.setLifecycleOwner(this)


        val libraryRecyclerView = binding.libraryRingtonesRV
        libraryRecyclerView.layoutManager= LinearLayoutManager(this)
        libraryRecyclerView!!.setHasFixedSize(true)


        viewModel.allLibrarySongs.observe(this) {mDeveloperModel ->

            if(mDeveloperModel != null) {
                libraryAdapter?.setDeveloperList(mDeveloperModel as MutableList<LibraryResponseModel>)
            }


        }

        viewModel.returnRingsListenUrlLibrary().observe(this){
            if(!it.equals("")){
                viewModel.onClickDinle()
            }
        }
        viewModel.returnRingsDowloandUrlLibrary().observe(this){
            if(!it.equals("")){
                viewModel.onClickIndir(applicationContext)
            }
        }
        //listener,
        libraryAdapter = LibraryAdapter(viewModel)

        libraryRecyclerView.setAdapter(libraryAdapter)


    }
    override fun onPause() {
        super.onPause()
        viewModel.stopPlaySong()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.stopPlaySong()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopPlaySong()
    }
}