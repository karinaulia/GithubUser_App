package com.bangkit.githubuser_app.data.retrofit

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUsers(
        @Query("q") q: String
    ): Call<UsersResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(@Path("username") username: String): Call<DetailItem>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}