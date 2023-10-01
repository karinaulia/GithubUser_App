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

    fun setFavoritesUser(fav: FavoriteUser, favoriteState: Boolean) {
        appExecutors.diskIO.execute {
            fav.isLoved = favoriteState
            favoriteDao.insert(fav)
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