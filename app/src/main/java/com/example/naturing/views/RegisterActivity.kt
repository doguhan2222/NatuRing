package com.example.naturing.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.naturing.R
import com.example.naturing.data.remote.RetrofitFactory
import com.example.naturing.data.repository.RegisterRepository
import com.example.naturing.databinding.ActivityRegisterBinding
import com.example.naturing.viewmodels.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding

    val repository: RegisterRepository by lazy {
        RegisterRepository(RetrofitFactory.service)
    }


    private val viewModel: RegisterViewModel by lazy {
        val factory = RegisterViewModel.Factory(repository = repository)
        ViewModelProvider(this,factory).get(RegisterViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.setLifecycleOwner(this)
        binding.registerViewModel = viewModel

        //RegisterViewModel içindeki fonk. dinler ve değişiklik olduğunda UI'ı günceller.Ayrıca Kayit başarılı ise
        // GirişEkranına geçiş yapar.
        viewModel.finalRegisterResult().observe(this){ response ->
            if(response.equals("Tebrikler kayit oldunuz")){
                Toast.makeText(this,"OlDU", Toast.LENGTH_LONG)
                val intent = Intent(this, MainActivity::class.java)
                // start your next activity
                startActivity(intent)
            }else{
                Toast.makeText(this,"Olmadi", Toast.LENGTH_LONG)
            }

        }

    }
}