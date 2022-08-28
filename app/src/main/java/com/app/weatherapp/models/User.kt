package com.app.weatherapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "FirstName")
    val firstName: String,
    @ColumnInfo(name = "LastName")
    val lastName: String,
    @ColumnInfo(name = "Email")
    val emailAddress: String
)
