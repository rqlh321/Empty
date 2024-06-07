package com.example.empty.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "user",
    primaryKeys = ["user_id"]
)
data class User(
    @ColumnInfo(name = "user_id") val id: String,
    @ColumnInfo(name = "user_name") val name: String,
)