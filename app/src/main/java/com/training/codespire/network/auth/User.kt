package com.training.codespire.network.auth

data class User(
    val created_at: String,
    val email: String,
    val id: Int,
    val name: String,
    val updated_at: String
)