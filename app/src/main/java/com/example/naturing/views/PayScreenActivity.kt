package com.example.naturing.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.naturing.R
import com.example.naturing.data.entities.Ids
import com.example.naturing.data.remote.RetrofitFactory
import com.example.naturing.data.repository.PayScreenRepository
import com.example.naturing.data.repository.PremiumRingtonesRepository
import com.example.naturing.databinding.ActivityPayScreenBinding
import com.example.naturing.databinding.ActivityPremiumAllRingtonesBinding
import com.example.naturing.viewmodels.PayScreenViewModel
import com.example.naturing.viewmodels.PremiumRingtonesViewModel

class PayScreenActivity : AppCompatActivity() {
    var gelen_id = ""

    lateinit var binding: ActivityPayScreenBinding

    val repository: PayScreenRepository by lazy {
        PayScreenRepository(RetrofitFactory.service)
    }

    private val viewModel: PayScreenViewModel by lazy {
        val factory = PayScreenViewModel.Factory(repository = repository)
        ViewModelProvider(this, factory).get(PayScreenViewModel::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_screen)

        gelen_id = intent.getStringExtra("ring_id").toString()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_pay_screen)
        setContentView(binding.root)
        binding.setLifecycleOwner(this)
        binding.payScreenViewModel = viewModel

        viewModel.payClickFonkReturn().observe(this) {
            if (it.equals("1")) {
                viewModel.payRingtones(Ids.user_id, gelen_id)
            }
        }
        viewModel.returnPayResult().observe(this){
            if(!it.equals("")){
                Toast.makeText(this,"Satın Alındı", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, RingtonesScreenActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}