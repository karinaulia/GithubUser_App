package com.bangkit.githubuser_app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.githubuser_app.data.retrofit.ItemsItem
import com.bangkit.githubuser_app.databinding.ActivityFavoriteBinding
import com.bangkit.githubuser_app.di.Injection

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter

    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavoriteUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavoriteUsers.addItemDecoration(itemDecoration)

        val factory = ViewModelFactory(Injection.provideRepository(this))
        favoriteViewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        adapter = UserAdapter()

        favoriteViewModel.getFavoriteUsers().observe(this) { users ->
            val items = arrayListOf<ItemsItem>()
            users.map {
                val item = ItemsItem(login = it.username, avatarUrl = it.avatarUrl.orEmpty())
                items.add(item)
            }

            adapter.submitList(items)
        }

        binding.rvFavoriteUsers.adapter = adapter
    }
}