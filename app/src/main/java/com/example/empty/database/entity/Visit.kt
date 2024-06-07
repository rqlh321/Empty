package com.example.empty.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "visit",
    primaryKeys = ["visit_id"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["visitor_id"]
        ),
        ForeignKey(
            entity = Place::class,
            parentColumns = ["place_id"],
            childColumns = ["place_id"]
        )
    ],
    indices = [
        Index(value = ["place_id"]),
        Index(value = ["visitor_id"])
    ]
)
data class Visit(
    @ColumnInfo(name = "visit_id") val id: String,
    @ColumnInfo(name = "place_id") val placeId: String,
    @ColumnInfo(name = "visitor_id") val userId: String,
    @ColumnInfo(name = "time_stamp") val timeStamp: Long,
)