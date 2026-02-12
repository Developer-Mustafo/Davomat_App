package uz.coder.davomatapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.coder.davomatapp.data.db.AppDatabase
import uz.coder.davomatapp.data.network.ApiClient
import uz.coder.davomatapp.data.network.ApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppProvidesModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase{
        return AppDatabase.getInstance(context)
    }
    @Provides
    @Singleton
    fun provideApiService(): ApiService{
        return ApiClient.getRetrofit().create(ApiService::class.java)
    }
}