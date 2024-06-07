package com.example.empty.database.related

import androidx.room.Embedded
import androidx.room.Relation
import com.example.empty.database.entity.Place
import com.example.empty.database.entity.Visit

data class PlaceWithVisits(
    @Embedded val place: Place,
    @Relation(
        parentColumn = "place_id",
        entityColumn = "place_id",
    )
    val visit: List<Visit>
)