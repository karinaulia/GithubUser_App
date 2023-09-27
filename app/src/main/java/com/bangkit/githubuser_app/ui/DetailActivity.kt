package com.bangkit.githubuser_app.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.githubuser_app.R
import com.bangkit.githubuser_app.data.retrofit.DetailItem
import com.bangkit.githubuser_app.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        const val data = "username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(data)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, username ?: "")
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
        username?.let {
            detailViewModel.getDetailUser(it)
        }
        detailViewModel.detailUser.observe(this) {userData ->
            setDetailUser(userData)
        }
        detailViewModel.isLoading.observe(this) {showLoading(it)}
    }

    private fun setDetailUser(userData: DetailItem) {
        Glide.with(this)
            .load(userData.avatarUrl)
            .into(binding.profileImage)
        binding.tvUsername.text = userData.login
        binding.tvName.text = userData.name
        binding.tvFollowersCount.text = userData.followers.toString()
        binding.tvFollowingCount.text = userData.following.toString()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}