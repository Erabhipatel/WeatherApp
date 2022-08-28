package com.app.weatherapp.repositories

import com.app.weatherapp.db.UserDao
import com.app.weatherapp.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MainRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: User) {
        withContext(Dispatchers.IO) {
            userDao.insertUser(user)
        }
    }

    suspend fun deleteUser(user: User) {
        withContext(Dispatchers.IO) {
            userDao.delete(user)
        }
    }

    fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUser()
    }
}