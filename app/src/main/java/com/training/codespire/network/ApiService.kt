package com.training.codespire.network

import com.training.codespire.network.auth.LoginRequest
import com.training.codespire.network.auth.LoginResponse
import com.training.codespire.network.auth.RegisterRequest
import com.training.codespire.network.auth.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<RegisterResponse>


    @POST("login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("logout")
    suspend fun logoutUser(@Header("Authorization") token: String): Response<Void>




}