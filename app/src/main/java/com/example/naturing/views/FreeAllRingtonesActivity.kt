package com.example.naturing.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.naturing.R
import com.example.naturing.adapters.FreeAllAdapter
import com.example.naturing.data.entities.AllRingtonesResponseModel
import com.example.naturing.data.remote.RetrofitFactory
import com.example.naturing.data.repository.FreeRingtonesRepository
import com.example.naturing.databinding.ActivityFreeAllRingtonesBinding
import com.example.naturing.viewmodels.FreeRingtonesViewModel

class FreeAllRingtonesActivity : AppCompatActivity() {

    lateinit var binding: ActivityFreeAllRingtonesBinding

    val repository: FreeRingtonesRepository by lazy {
        FreeRingtonesRepository(RetrofitFactory.service)
    }

    private val viewModel: FreeRingtonesViewModel by lazy {
        val factory = FreeRingtonesViewModel.Factory(repository = repository)
        ViewModelProvider(this,factory).get(FreeRingtonesViewModel::class.java)
    }

    private var ucretsizTamListeAdapter: FreeAllAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_free_all_ringtones)
        setContentView(binding.root)
        binding.setLifecycleOwner(this)


        val ucretsizTamListeRecyclerView = binding.ucretsizRingtonesAllRv
        ucretsizTamListeRecyclerView.layoutManager= LinearLayoutManager(this)
        ucretsizTamListeRecyclerView!!.setHasFixedSize(true)


/*
        val listener = object: FreeAllAdapter.CustomViewHolderListener {
            override fun onCustomItemClicked(x: String) {}

        }*/

        var ucretsizTamListe = mutableListOf<AllRingtonesResponseModel>()

        viewModel.allFreeRingtones.observe(this) { mDeveloperModel ->

            for (x in mDeveloperModel){
                //ücretsiz olan
                if(x.s_premiumDurum =="0"){
                    ucretsizTamListe.add(x)
                }
            }
            ucretsizTamListeAdapter?.setDeveloperList(ucretsizTamListe as MutableList<AllRingtonesResponseModel>)

        }

        viewModel.returnRings().observe(this){
            if(!it.equals("")){
                viewModel.onClick()
            }
        }
        viewModel.returnRingsDowloandUrl().observe(this){
            if(!it.equals("")){
                viewModel.onClickIndir(applicationContext)
            }
        }
        //listener,
        ucretsizTamListeAdapter = FreeAllAdapter(viewModel)

        ucretsizTamListeRecyclerView.setAdapter(ucretsizTamListeAdapter)


    }


}