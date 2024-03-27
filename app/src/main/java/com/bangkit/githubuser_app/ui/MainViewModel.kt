package com.bangkit.githubuser_app.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.githubuser_app.data.retrofit.ApiConfig
import com.bangkit.githubuser_app.data.retrofit.ItemsItem
import com.bangkit.githubuser_app.data.retrofit.UsersResponse
import com.bangkit.githubuser_app.repository.FavoritesRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val favoritesRepository: FavoritesRepository) : ViewModel(){

    private val _user = MutableLiveData<List<ItemsItem>>()
    val user: LiveData<List<ItemsItem>> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
        private const val USER_ID = "arif"
    }

    init {
        findUsers()
    }

    fun findUsers(query: String? = null){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(query ?:USER_ID)
        client.enqueue(object : Callback<UsersResponse> {
            override fun onResponse(
                call: Call<UsersResponse>,
                response: Response<UsersResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getTheme(): LiveData<Boolean> = favoritesRepository.getThemeSettings().asLiveData()
}