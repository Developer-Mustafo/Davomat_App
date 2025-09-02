package uz.coder.davomatapp.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    init {
        System.loadLibrary("native-lib")
    }
    @JvmStatic
    external fun getApiBaseUrl(): String
    fun getRetrofit(): Retrofit{
        val okhttp = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(getApiBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttp)
            .build()
        return retrofit
    }
}