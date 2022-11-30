package com.example.naturing.data.remote

import com.example.naturing.data.entities.AllRingtonesResponseModel
import com.example.naturing.data.entities.LoginResponseModel
import com.example.naturing.data.entities.RegisterResponseModel
import com.example.naturing.utilities.BaseUrl
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("/natu_ring_login.php")
    fun login(@Field("kullaniciAdi") username:String, @Field("sifre") password:String): Call<LoginResponseModel>

    @FormUrlEncoded
    @POST("/natu_ring_register.php")
    fun register(@Field("isim") name:String,
                 @Field("soyisim") surname:String,
                 @Field("kullaniciAdi") username:String,
                 @Field("sifre") password:String,
                 @Field("email") email:String,
                 @Field("telefon") phone:String): Call<RegisterResponseModel>

    @GET("/natu_ring_ana_sayfa.php")
    fun allRingtones(): Call<MutableList<AllRingtonesResponseModel>>

    @GET("/natu_ring_ucretsiz_sayfa.php")
    fun allFreeRingtones(): Call<MutableList<AllRingtonesResponseModel>>
}


class RetrofitFactory {
    companion object {
        val client = OkHttpClient.Builder().build()
        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BaseUrl.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val service: ApiService by lazy {
            retrofit.create(ApiService::class.java)
        }
    }
}