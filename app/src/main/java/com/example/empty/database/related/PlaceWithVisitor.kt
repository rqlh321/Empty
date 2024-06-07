package com.example.empty.database.related

import androidx.room.Embedded
import androidx.room.Relation
import com.example.empty.database.entity.Place
import com.example.empty.database.view.Visitor

data class PlaceWithVisitor(
    @Embedded val place: Place,
    @Relation(
        parentColumn = "place_id",
        entityColumn = "place_id",
    )
    val visit: List<Visitor>
)