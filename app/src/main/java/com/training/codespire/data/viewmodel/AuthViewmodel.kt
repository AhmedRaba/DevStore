package com.training.codespire.data.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.training.codespire.data.datastore.SharedPreferencesUtil
import com.training.codespire.data.repos.AuthRepository
import com.training.codespire.network.auth.LoginRequest
import com.training.codespire.network.auth.LoginResponse
import com.training.codespire.network.auth.RegisterRequest
import com.training.codespire.network.auth.RegisterResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthViewmodel(application: Application) : AndroidViewModel(application) {

    private val repository = AuthRepository(application)

    val registerResponseLiveData = MutableLiveData<RegisterResponse>()
    val loginResponseLiveData = MutableLiveData<LoginResponse>()
    val logoutResponseLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()


    fun registerUser(username: String, email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            try {
                val registerRequest = RegisterRequest(username, email, password, confirmPassword)
                val response: Response<RegisterResponse> = repository.registerUser(registerRequest)
                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    registerResponseLiveData.postValue(registerResponse!!)
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown Error"
                    errorLiveData.postValue(errorMessage)
                }
            } catch (e: Exception) {
                errorLiveData.postValue("Network error: ${e.message}")
            }

        }
    }


    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(email, password)
                val response: Response<LoginResponse> = repository.loginUser(loginRequest)
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    loginResponseLiveData.postValue(loginResponse!!)
                    val username = loginResponse.user.name
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown Error"
                    errorLiveData.postValue(errorMessage)
                }
            } catch (e: Exception) {
                errorLiveData.postValue("Network error: ${e.message}")
            }
        }
    }

    fun logoutUser() {
        viewModelScope.launch {
            try {
                Log.d("AuthViewModel", "Attempting to logout")
                val response = repository.logoutUser()
                if (response.isSuccessful) {
                    Log.d("AuthViewModel", "Logout successful")
                    logoutResponseLiveData.postValue(true)
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown Error"
                    Log.e("AuthViewModel", "Logout failed: $errorMessage")
                    errorLiveData.postValue(errorMessage)
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Network error: ${e.message}")
                errorLiveData.postValue("Network error: ${e.message}")
            }
        }
    }

}