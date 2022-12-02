package com.example.naturing.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.naturing.R
import com.example.naturing.adapters.FreeAdapterHomePage
import com.example.naturing.adapters.PremiumAdapterHomePage
import com.example.naturing.data.entities.AllRingtonesResponseModel
import com.example.naturing.data.remote.RetrofitFactory
import com.example.naturing.data.repository.AllRingtonesRepository
import com.example.naturing.databinding.ActivityRingtonesScreenBinding
import com.example.naturing.viewmodels.AllRingtonesViewModel

class RingtonesScreenActivity : AppCompatActivity() {

    lateinit var binding: ActivityRingtonesScreenBinding

    val repository: AllRingtonesRepository by lazy {
        AllRingtonesRepository(RetrofitFactory.service)
    }

    private val viewModel: AllRingtonesViewModel by lazy {
        val factory = AllRingtonesViewModel.Factory(repository = repository)
        ViewModelProvider(this,factory).get(AllRingtonesViewModel::class.java)
    }

    private var mDeveloper_CustomAdapter: FreeAdapterHomePage? = null
    private var mDeveloper_CustomAdapter2: PremiumAdapterHomePage? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_ringtones_screen)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_ringtones_screen)
        setContentView(binding.root)
        binding.setLifecycleOwner(this)
        binding.allRingtonesViewModel = viewModel


        val recyclerView = binding.anaSayfaUcretsizRecyclerView
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView!!.setHasFixedSize(true)


        val recyclerView2 = binding.anaSayfaUcretliRecyclerView
        recyclerView2.layoutManager=LinearLayoutManager(this)
        recyclerView2!!.setHasFixedSize(true)


       /* val listener = object: MainAdapter.CustomViewHolderListener {
            override fun onCustomItemClicked(x: String) {}

        }
        val listener2 = object: MainAdapter2.CustomViewHolderListener {
            override fun onCustomItemClicked(x: String) {}

        }*/
        var bb = mutableListOf<AllRingtonesResponseModel>()
        var cc = mutableListOf<AllRingtonesResponseModel>()
        viewModel.allDeveloper.observe(this) { mDeveloperModel ->
            ///if any thing chnage the update the UI
            Log.e("aaaaa", mDeveloperModel.toString())
           // mDeveloper_CustomAdapter?.setDeveloperList(mDeveloperModel as MutableList<AllRingtonesResponseModel>)
            for (x in mDeveloperModel){
                //ücretsiz olan
                if(x.s_premiumDurum =="0"){
                    bb.add(x)
                }
            }
            mDeveloper_CustomAdapter?.setDeveloperList(bb as MutableList<AllRingtonesResponseModel>)


        }
        viewModel.allDeveloper.observe(this) { mDeveloperModel ->
            ///if any thing chnage the update the UI
            Log.e("aaaaa", mDeveloperModel.toString())
            for (x in mDeveloperModel){
                if(x.s_premiumDurum =="1"){
                    cc.add(x)
                }
            }
            mDeveloper_CustomAdapter2?.setDeveloperList(cc as MutableList<AllRingtonesResponseModel>)


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

        //listener2,
        mDeveloper_CustomAdapter2 = PremiumAdapterHomePage(viewModel)
        mDeveloper_CustomAdapter = FreeAdapterHomePage(viewModel)
        //mDeveloper_CustomAdapter = MainAdapter()
         //var mDeveloper_CustomAdapter2: AdapterDeneme? = null
        recyclerView.setAdapter(mDeveloper_CustomAdapter)
        recyclerView2.setAdapter(mDeveloper_CustomAdapter2)

        //getAllDev()


        viewModel.ucretsizClickReturn().observe(this){ response->
            if(response.equals("1")) {
                val intent = Intent(this, FreeAllRingtonesActivity::class.java)

                startActivity(intent)
            }

        }
        viewModel.ucreliClickReturn().observe(this){ response->
            if(response.equals("1")) {
                val intent = Intent(this, PremiumAllRingtonesActivity::class.java)

                startActivity(intent)
            }

        }

    }

   /* private fun getAllDev() {
        ///get the list of dev from api response
        viewModel!!.allDeveloper.observe(this) { mDeveloperModel ->
            ///if any thing chnage the update the UI
            Log.e("aaaaa", mDeveloperModel.toString())
            mDeveloper_CustomAdapter?.setDeveloperList(mDeveloperModel as MutableList<AllRingtonesResponseModel>)

        }

    }*/


}