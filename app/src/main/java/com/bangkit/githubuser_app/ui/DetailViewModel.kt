package com.bangkit.githubuser_app.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.githubuser_app.data.retrofit.ApiConfig
import com.bangkit.githubuser_app.data.retrofit.DetailItem
import com.bangkit.githubuser_app.database.FavoriteUser
import com.bangkit.githubuser_app.repository.FavoritesRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val favoritesRepository: FavoritesRepository): ViewModel() {
    private val _detailUser = MutableLiveData<DetailItem>()
    val detailUser: LiveData<DetailItem> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        const val TAG = "DetailViewModel"
    }

    fun getDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailItem> {
            override fun onResponse(
                call: Call<DetailItem>,
                response: Response<DetailItem>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailItem>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getFavoritesUser(username: String) = favoritesRepository.isUserFavorite(username)

    fun saveUser(favoriteUser: FavoriteUser) {
        Log.d("DetailViewModel", "Saving user: $favoriteUser")
        favoritesRepository.setFavoritesUser(favoriteUser.username, favoriteUser.avatarUrl ?: "")
        Log.d("DetailViewModel", "User saved successfully")
    }

    fun deleteUser(favoriteUser: FavoriteUser) {
        favoritesRepository.deleteFavoritesUser(favoriteUser.username, favoriteUser.avatarUrl ?: "")
    }
}