package com.bangkit.githubuser_app.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.githubuser_app.R
import com.bangkit.githubuser_app.data.retrofit.ItemsItem
import com.bangkit.githubuser_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var searchView: List<ItemsItem> = emptyList()
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val viewModelFactory = ViewModelFactory.getInstance(applicationContext)
        val mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        mainViewModel.user.observe(this) {user ->
            setUsersData(user)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        mainViewModel.getTheme().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    mainViewModel.findUsers(searchView.text.toString())
                    false
                }
        }

        val favoriteButton = findViewById<ImageView>(R.id.favoriteButton)
        favoriteButton.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }

        val settingsButton = findViewById<ImageView>(R.id.settingsButton)
        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        adapter = UserAdapter()
        binding.rvUsers.adapter = adapter
    }

    private fun setUsersData(items: List<ItemsItem>) {
        searchView = items
        adapter.submitList(searchView)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}