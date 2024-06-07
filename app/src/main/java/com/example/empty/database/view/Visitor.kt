package com.example.empty.database.view

import androidx.room.ColumnInfo
import androidx.room.DatabaseView

@DatabaseView(
    value = "SELECT " +
            "visit.time_stamp, " +
            "place.*, " +
            "user.* " +
            "FROM visit " +
            "JOIN user ON visitor_id = user_id " +
            "JOIN place ON visit.place_id = place.place_id ",
    viewName = "visitor"
)
data class Visitor(
    @ColumnInfo(name = "place_id") val placeId: String,
    @ColumnInfo(name = "place_name") val placeName: String,
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "time_stamp") val timeStamp: Long,
)