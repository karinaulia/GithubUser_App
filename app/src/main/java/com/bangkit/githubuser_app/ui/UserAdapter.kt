package com.bangkit.githubuser_app.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.githubuser_app.data.retrofit.ItemsItem
import com.bangkit.githubuser_app.databinding.UserProfileBinding
import com.bumptech.glide.Glide

class UserAdapter() : ListAdapter<ItemsItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = UserProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val items = getItem(position)
        holder.bind(items)
    }

    class MyViewHolder(private val binding: UserProfileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem){
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .into(binding.profileImage)
            binding.tvItem.text = user.login
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("username", user.login)
                intent.putExtra(DetailActivity.avatarData, user.avatarUrl)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}