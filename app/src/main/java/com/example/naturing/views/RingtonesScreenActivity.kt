package com.example.naturing.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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

        // Burası arama ikonuna tıklanınca yapılacak işler için
        binding.ringtoneSearch.setEndIconOnClickListener(View.OnClickListener {
            if(!binding.ringtoneSearchBar.text!!.isEmpty()){
                val intent = Intent(this, SearchScreenActivity::class.java)
                intent.putExtra("search_ring",binding.ringtoneSearchBar.text.toString())
                binding.ringtoneSearchBar.setText("")
                startActivity(intent)
            }else{
                Toast.makeText(this,"Lütfen bir kelime girin",Toast.LENGTH_SHORT).show()
            }

        })

        var bb = mutableListOf<AllRingtonesResponseModel>()
        var cc = mutableListOf<AllRingtonesResponseModel>()
        viewModel.allDeveloper.observe(this) { mDeveloperModel ->

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
        viewModel.returnRingPay().observe(this){
            if(!it.equals("")){
                val intent = Intent(this, PayScreenActivity::class.java)
                intent.putExtra("ring_id",it)
                startActivity(intent)
            }
        }

        viewModel.kitaplikToolbarOnClickReturn().observe(this){
            if(it.equals("1")){
                val intent = Intent(this, LibraryActivity::class.java)
                startActivity(intent)
            }
        }

        viewModel.exitToolbarOnClickReturn().observe(this){
            if(it.equals("1")){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
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

    override fun onPause() {
        viewModel.stopPlaySong()
        super.onPause()

    }

    override fun onDestroy() {
        viewModel.stopPlaySong()
        super.onDestroy()

    }

    override fun onStop() {
        viewModel.stopPlaySong()
        super.onStop()


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