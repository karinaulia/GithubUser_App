package com.bangkit.githubuser_app.di

import android.content.Context
import com.bangkit.githubuser_app.SettingPreference
import com.bangkit.githubuser_app.dataStore
import com.bangkit.githubuser_app.database.FavoriteRoomDatabase
import com.bangkit.githubuser_app.repository.FavoritesRepository
import com.bangkit.githubuser_app.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavoritesRepository {
        val database = FavoriteRoomDatabase.getDatabase(context)
        val dao = database.favoriteDao()
        val appExecutors = AppExecutors()
        val dataStore = context.dataStore
        val settingPreference = SettingPreference.getInstance(dataStore)
        return FavoritesRepository.getInstance(dao, appExecutors, settingPreference)
    }
}