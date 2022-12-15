package com.example.naturing.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.naturing.R
import com.example.naturing.data.remote.RetrofitFactory
import com.example.naturing.data.repository.LoginRepository
import com.example.naturing.databinding.ActivityMainBinding
import com.example.naturing.utilities.BaseUrl
import com.example.naturing.viewmodels.LoginViewModel

//android:dataExtractionRules="@xml/data_extraction_rules"
//        android:fullBackupContent="@xml/backup_rules"
//        tools:targetApi="31"
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    val repository: LoginRepository by lazy {
        LoginRepository(RetrofitFactory.service)
    }


    private val viewModel: LoginViewModel by lazy {
        val factory = LoginViewModel.Factory(repository = repository)
        ViewModelProvider(this,factory).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.setLifecycleOwner(this)
        binding.loginViewModel = viewModel

        //LoginViewNModel içindeki fonk. dinler ve değişiklik olduğunda UI'ı günceller.Ayrıca Kayit başarılı ise
        viewModel.finalResult().observe(this){ response ->
            if(response !=null){
                if(response.equals("Tebrikler")){
                    Toast.makeText(this,"Giriş Yapılıyor",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, RingtonesScreenActivity::class.java)
                    /*binding.resultxml.setText("Tebrikler")
                    val intent = Intent(this, RingtonesScreenActivity::class.java)
                    // start your next activity
                    //viewModel.result.postValue("")*/
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this,"Giriş Yapılamadı",Toast.LENGTH_SHORT).show()
                }
            }


        }
        viewModel.sayfaDurum().observe(this){ response ->
            if(response.equals("1")) {
                val intent = Intent(this, RegisterActivity::class.java)
                // start your next activity
                startActivity(intent)
            }

        }



    }
}