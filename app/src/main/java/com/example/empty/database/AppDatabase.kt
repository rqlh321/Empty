package com.example.empty.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.empty.database.dao.ComplexDao
import com.example.empty.database.dao.MetadataDao
import com.example.empty.database.dao.PlaceDao
import com.example.empty.database.dao.UserDao
import com.example.empty.database.dao.VisitDao
import com.example.empty.database.entity.AppMetadata
import com.example.empty.database.entity.Place
import com.example.empty.database.entity.User
import com.example.empty.database.entity.Visit
import com.example.empty.database.view.Visitor

@Database(
    entities = [
        AppMetadata::class,
        Place::class,
        User::class,
        Visit::class,
    ],
    views = [
        Visitor::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun visitDao(): VisitDao
    abstract fun countryDao(): PlaceDao
    abstract fun metadataDao(): MetadataDao
    abstract fun complexDao(): ComplexDao
}