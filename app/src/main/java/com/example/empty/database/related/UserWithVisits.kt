package com.example.empty.database.related

import androidx.room.Embedded
import androidx.room.Relation
import com.example.empty.database.entity.User
import com.example.empty.database.entity.Visit

data class UserWithVisits(
    @Embedded val user: User,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "visitor_id",
    )
    val visit: List<Visit>
)