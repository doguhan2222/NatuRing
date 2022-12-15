package com.example.naturing.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.naturing.R
import com.example.naturing.adapters.FreeAllAdapter
import com.example.naturing.adapters.PremiumAllAdapter
import com.example.naturing.data.entities.AllRingtonesResponseModel
import com.example.naturing.data.remote.RetrofitFactory
import com.example.naturing.data.repository.PremiumRingtonesRepository
import com.example.naturing.databinding.ActivityPremiumAllRingtonesBinding
import com.example.naturing.viewmodels.PremiumRingtonesViewModel

class PremiumAllRingtonesActivity : AppCompatActivity()  {

    lateinit var binding: ActivityPremiumAllRingtonesBinding

    val repository: PremiumRingtonesRepository by lazy {
        PremiumRingtonesRepository(RetrofitFactory.service)
    }

    private val viewModel: PremiumRingtonesViewModel by lazy {
        val factory = PremiumRingtonesViewModel.Factory(repository = repository)
        ViewModelProvider(this,factory).get(PremiumRingtonesViewModel::class.java)
    }

    private var ucretliTamListeAdapter: PremiumAllAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_premium_all_ringtones)
        setContentView(binding.root)
        binding.setLifecycleOwner(this)


        val ucretliTamListeRecyclerView = binding.ucretliRingtonesAllRv
        ucretliTamListeRecyclerView.layoutManager= LinearLayoutManager(this)
        ucretliTamListeRecyclerView!!.setHasFixedSize(true)


/*
        val listener = object: FreeAllAdapter.CustomViewHolderListener {
            override fun onCustomItemClicked(x: String) {}

        }*/

        var ucretliTamListe = mutableListOf<AllRingtonesResponseModel>()

        viewModel.allPremiumRingtones.observe(this) { response ->

            for (x in response){
                //ücretli olan
                if(x.s_premiumDurum =="1"){
                    ucretliTamListe.add(x)
                }
            }
            ucretliTamListeAdapter?.setDeveloperList(ucretliTamListe as MutableList<AllRingtonesResponseModel>)

        }

        viewModel.returnRings().observe(this){
            if(!it.equals("")){
                viewModel.onClick()
            }
        }
        //listener,
        ucretliTamListeAdapter = PremiumAllAdapter(viewModel)

        ucretliTamListeRecyclerView.setAdapter(ucretliTamListeAdapter)

        viewModel.returnRingPay().observe(this){
            if(!it.equals("")){
                val intent = Intent(this, PayScreenActivity::class.java)
                intent.putExtra("ring_id",it)
                startActivity(intent)
            }
        }

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