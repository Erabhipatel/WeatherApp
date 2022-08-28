package com.app.weatherapp.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.app.weatherapp.models.LoginResponse
import com.app.weatherapp.db.UserDatabase
import com.app.weatherapp.models.User
import com.app.weatherapp.repositories.MainRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var userName: String = ""
    var password: String = ""
    var firstName = ""
    var lastName = ""
    var email = ""
    var isLoading: ObservableField<Boolean> = ObservableField(false)
    private val userDao by lazy {
        UserDatabase.invoke(application).userDao()
    }
    private val repository: MainRepository by lazy {
        MainRepository(userDao)
    }

    private val _loginResponse: MutableLiveData<LoginResponse> by lazy {
        MutableLiveData<LoginResponse>()
    }
    val loginResponse: LiveData<LoginResponse>
        get() = _loginResponse

    private val _userListLiveData: MutableLiveData<List<User>> by lazy {
        MutableLiveData<List<User>>()
    }

    val addUserLiveData: LiveData<String>
        get() = _addUserLiveData

    private val _addUserLiveData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val userListLiveData: LiveData<List<User>>
        get() = _userListLiveData


    fun signIn() {
        viewModelScope.launch {
            isLoading.set(true)
            if (userName.isEmpty()) {
                _loginResponse.value = LoginResponse(201, "Username is Empty")
            } else if (password.isEmpty()) {
                _loginResponse.value = LoginResponse(202, "Password is Empty")
            } else if (userName == ("testapp@google.com")
                && password == ("Test@123456")) {
                delay(2000)
                _loginResponse.value = LoginResponse(200, "Logged In Successfully")
            } else {
                _loginResponse.value = LoginResponse(203, "Invalid Username or Password")
            }
        }

    }

    fun addUser(){
        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()){
            _addUserLiveData.value = "Please provide user details"
        }else{
            insertUser(User(0,firstName, lastName, email))
            _addUserLiveData.value = "User added successfully"
            firstName = ""
            lastName = ""
            email = ""
        }
        _addUserLiveData.value = null
    }

    private fun insertUser(user: User){
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }

    fun deleteUser(user: User){
        viewModelScope.launch {
            repository.deleteUser(user)
        }
    }

    fun getAllUsers(){
        viewModelScope.launch {
            repository.getAllUsers().collect {
                _userListLiveData.value = it
                println("it")
            }
        }

    }




}