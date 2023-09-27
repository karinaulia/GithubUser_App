package com.bangkit.githubuser_app.data.retrofit

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization:token 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")
    fun getUsers(
        @Query("q") q: String
    ): Call<UsersResponse>

    @GET("users/{username}")
    @Headers("Authorization:token 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")
    fun getDetailUser(@Path("username") username: String): Call<DetailItem>

    @GET("users/{username}/followers")
    @Headers("Authorization:token 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")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization:token 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")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}