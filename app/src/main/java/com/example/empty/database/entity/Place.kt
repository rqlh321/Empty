package com.example.empty.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "place",
    primaryKeys = ["place_id"]
)
data class Place(
    @ColumnInfo(name = "place_id") val id: String,
    @ColumnInfo(name = "place_name") val name: String,
)