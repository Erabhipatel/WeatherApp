package com.app.weatherapp.db

import androidx.room.*
import com.app.weatherapp.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * from user_table")
    fun getAllUser(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Delete
    suspend fun delete(user: User)

}