package com.bangkit.githubuser_app.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.githubuser_app.repository.FavoritesRepository
import kotlinx.coroutines.launch

class SettingsViewModel(private val favoritesRepository: FavoritesRepository) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return favoritesRepository.getThemeSettings().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            favoritesRepository.saveTheme(isDarkModeActive)
        }
    }
}