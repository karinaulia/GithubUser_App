package com.bangkit.githubuser_app.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.githubuser_app.database.FavoriteUser
import com.bangkit.githubuser_app.repository.FavoritesRepository

class FavoriteViewModel(private val repository: FavoritesRepository) : ViewModel() {

    fun getFavoriteUsers(): LiveData<List<FavoriteUser>> {
        return repository.getFavoritesUser()
    }
}