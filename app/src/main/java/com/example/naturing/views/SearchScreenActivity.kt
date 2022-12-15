package com.example.naturing.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.naturing.R
import com.example.naturing.adapters.SearchScreenFreeAdapter
import com.example.naturing.adapters.SearchScreenPremiumAdapter
import com.example.naturing.data.entities.SearchScreenResponseModel
import com.example.naturing.data.remote.RetrofitFactory
import com.example.naturing.data.repository.SearchScreenRepository
import com.example.naturing.databinding.ActivitySearchScreenBinding
import com.example.naturing.viewmodels.SearchScreenViewModel

class SearchScreenActivity : AppCompatActivity() {



    var gelenSearchRingName = ""
    lateinit var binding: ActivitySearchScreenBinding

    val repository: SearchScreenRepository by lazy {
        SearchScreenRepository(RetrofitFactory.service)
    }

    private val viewModel: SearchScreenViewModel by lazy {
        val factory = SearchScreenViewModel.Factory(repository = repository)
        ViewModelProvider(this, factory).get(SearchScreenViewModel::class.java)
    }

    private var mDeveloper_CustomAdapter: SearchScreenFreeAdapter? = null
    private var mDeveloper_CustomAdapter2: SearchScreenPremiumAdapter? = null

    var ucretsizList = mutableListOf<SearchScreenResponseModel>()
    var ucretliList = mutableListOf<SearchScreenResponseModel>()

    lateinit var recyclerView:RecyclerView
    lateinit var recyclerView2:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_ringtones_screen)

        gelenSearchRingName = intent.getStringExtra("search_ring").toString()


        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_screen)
        setContentView(binding.root)
        binding.setLifecycleOwner(this)
        binding.searchScreenViewModel = viewModel
        if (!gelenSearchRingName.equals("")) {
            viewModel.getSearchRingFromRepo(gelenSearchRingName)

        }

        //val recyclerView = binding.searchSayfaUcretsizRecyclerView
        recyclerView = binding.searchSayfaUcretsizRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView!!.setHasFixedSize(true)


        //val recyclerView2 = binding.searchSayfaUcretliRecyclerView
         recyclerView2 = binding.searchSayfaUcretliRecyclerView
        recyclerView2.layoutManager = LinearLayoutManager(this)
        recyclerView2!!.setHasFixedSize(true)



        Log.e("hahha",viewModel.allSearchedRings.value.toString())
        viewModel.returnSearchRing().observe(this) { mDeveloperModel ->
            ///if any thing chnage the update the UI
            //Log.e("aaaaa", mDeveloperModel.toString())
            Log.e("1233","bbbbbb")
            // mDeveloper_CustomAdapter?.setDeveloperList(mDeveloperModel as MutableList<AllRingtonesResponseModel>)
            if (mDeveloperModel != null) {
                Log.e("1233","xxxxx")
                for (x in mDeveloperModel) {
                    //ücretsiz olan
                    if (x.s_premiumDurum == "0") {
                        ucretsizList.add(x)
                    }
                }
                Log.e("arama2", ucretsizList.toString())
                mDeveloper_CustomAdapter?.setDeveloperList(ucretsizList as MutableList<SearchScreenResponseModel>)

            }


        }
        viewModel.returnSearchRing().observe(this) { mDeveloperModel ->
            ///if any thing chnage the update the UI
            //Log.e("aaaaa", mDeveloperModel.toString())
            Log.e("12333","cccccc")
            if (mDeveloperModel != null) {

                for (x in mDeveloperModel) {
                    if (x.s_premiumDurum == "1") {
                        ucretliList.add(x)
                    }
                }
                Log.e("arama2", ucretliList.toString())
                mDeveloper_CustomAdapter2?.setDeveloperList(ucretliList as MutableList<SearchScreenResponseModel>)


            }


        }
        viewModel.returnRings().observe(this) {
            if (!it.equals("")) {
                viewModel.onClick()
            }
        }
        viewModel.returnRingsDowloandUrl().observe(this) {
            if (!it.equals("")) {
                viewModel.onClickIndir(applicationContext)
            }
        }
        viewModel.returnRingPay().observe(this) {
            if (!it.equals("")) {
                val intent = Intent(this, PayScreenActivity::class.java)
                intent.putExtra("ring_id", it)
                startActivity(intent)
            }
        }

        //listener2,
        mDeveloper_CustomAdapter2 = SearchScreenPremiumAdapter(viewModel)
        mDeveloper_CustomAdapter = SearchScreenFreeAdapter(viewModel)
        //mDeveloper_CustomAdapter = MainAdapter()
        //var mDeveloper_CustomAdapter2: AdapterDeneme? = null
        recyclerView.setAdapter(mDeveloper_CustomAdapter)
        recyclerView2.setAdapter(mDeveloper_CustomAdapter2)



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


/* private fun getAllDev() {
     ///get the list of dev from api response
     viewModel!!.allDeveloper.observe(this) { mDeveloperModel ->
         ///if any thing chnage the update the UI
         Log.e("aaaaa", mDeveloperModel.toString())
         mDeveloper_CustomAdapter?.setDeveloperList(mDeveloperModel as MutableList<AllRingtonesResponseModel>)

     }*/



