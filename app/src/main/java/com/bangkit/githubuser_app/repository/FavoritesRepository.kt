package com.bangkit.githubuser_app.repository

import androidx.lifecycle.LiveData
import com.bangkit.githubuser_app.data.retrofit.ApiService
import com.bangkit.githubuser_app.database.FavoriteDao
import com.bangkit.githubuser_app.database.FavoriteUser
import com.bangkit.githubuser_app.utils.AppExecutors

class FavoritesRepository private constructor(
    private val apiService: ApiService,
    private val favoriteDao: FavoriteDao,
    private val appExecutors: AppExecutors
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
        val result = favoriteDao.isUserFavorite(username)
        return result
    }

    fun deleteFavoritesUser(username: String, avatarUrl: String) {
        appExecutors.diskIO.execute {
            val favoriteUser = FavoriteUser(username, avatarUrl)
            favoriteDao.delete(favoriteUser)
        }
    }

    companion object {
        @Volatile
        private var instance: FavoritesRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: FavoriteDao,
            appExecutors: AppExecutors
        ): FavoritesRepository =
            instance ?: synchronized(this) {
                instance ?: FavoritesRepository(apiService, newsDao, appExecutors)
            }.also { instance = it }
    }
}