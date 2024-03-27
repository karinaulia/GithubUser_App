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