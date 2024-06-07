package com.example.empty.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Upsert

interface BaseDao<T> {

    @Insert
    suspend fun insert(entities: List<T>)

    @Insert
    suspend fun insert(entity: T)

    @Update
    suspend fun update(entity: T)

    @Update
    suspend fun update(entities: List<T>)

    @Upsert
    suspend fun upsert(entity: T)

    @Upsert
    suspend fun upsert(entities: List<T>)

    @Delete
    suspend fun delete(entities: List<T>)

    @Delete
    suspend fun delete(entity: T)
}