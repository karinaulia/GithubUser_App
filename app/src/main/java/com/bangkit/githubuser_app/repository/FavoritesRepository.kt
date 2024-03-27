package com.bangkit.githubuser_app.repository

import androidx.lifecycle.LiveData
import com.bangkit.githubuser_app.SettingPreference
import com.bangkit.githubuser_app.database.FavoriteDao
import com.bangkit.githubuser_app.database.FavoriteUser
import com.bangkit.githubuser_app.utils.AppExecutors
import kotlinx.coroutines.flow.Flow

class FavoritesRepository private constructor(
    private val favoriteDao: FavoriteDao,
    private val appExecutors: AppExecutors,
    private val settingPreference: SettingPreference
){

    fun getFavoritesUser(): LiveData<List<FavoriteUser>> {
        return favoriteDao.getAllFavorites()
    }

    fun setFavoritesUser(username: String, avatarUrl: String) {
        appExecutors.diskIO.execute {
            val favoriteUser = FavoriteUser(username, avatarUrl)
            favoriteDao.insert(favoriteUser)
        }
    }

    fun isUserFavorite(username: String): LiveData<List<FavoriteUser>> {
        return favoriteDao.isUserFavorite(username)
    }

    fun deleteFavoritesUser(username: String, avatarUrl: String) {
        appExecutors.diskIO.execute {
            val favoriteUser = FavoriteUser(username, avatarUrl)
            favoriteDao.delete(favoriteUser)
        }
    }

    fun getThemeSettings(): Flow<Boolean> = settingPreference.getThemeSetting()

    suspend fun saveTheme(isDarkModeActive: Boolean) = settingPreference.saveThemeSetting(isDarkModeActive)

    companion object {
        @Volatile
        private var instance: FavoritesRepository? = null
        fun getInstance(
            newsDao: FavoriteDao,
            appExecutors: AppExecutors,
            settingPreference: SettingPreference
        ): FavoritesRepository =
            instance ?: synchronized(this) {
                instance ?: FavoritesRepository(newsDao, appExecutors, settingPreference)
            }.also { instance = it }
    }
}