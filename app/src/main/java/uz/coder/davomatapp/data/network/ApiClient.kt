package uz.coder.davomatapp.data.network

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import uz.coder.davomatapp.data.LocalDateSerializer
import uz.coder.davomatapp.todo.token
import java.time.LocalDate
import java.util.concurrent.TimeUnit

object ApiClient {
    init {
        System.loadLibrary("native-lib")
    }
    @JvmStatic
    private external fun getApiBaseUrl(): String
    private val gson = GsonBuilder()
        .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
        .create()
    fun getRetrofit(): Retrofit{
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            serializersModule = SerializersModule {
                contextual(LocalDate::class, LocalDateSerializer)
            }
        }
        val okhttp = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor{
                it.proceed(it.request().newBuilder().addHeader("Authorization", "Bearer $token").build())
            }
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(getApiBaseUrl())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okhttp)
            .build()
        return retrofit
    }
}