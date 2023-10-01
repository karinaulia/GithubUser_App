package com.bangkit.githubuser_app.di

import android.content.Context
import com.bangkit.githubuser_app.data.retrofit.ApiConfig
import com.bangkit.githubuser_app.database.FavoriteRoomDatabase
import com.bangkit.githubuser_app.repository.FavoritesRepository
import com.bangkit.githubuser_app.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavoritesRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteRoomDatabase.getDatabase(context)
        val dao = database.favoriteDao()
        val appExecutors = AppExecutors()
        return FavoritesRepository.getInstance(apiService, dao, appExecutors)
    }
}