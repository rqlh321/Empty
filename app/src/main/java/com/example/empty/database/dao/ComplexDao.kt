package com.example.empty.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.empty.database.related.PlaceWithVisitor
import com.example.empty.database.related.PlaceWithVisits
import com.example.empty.database.related.UserWithVisits
import kotlinx.coroutines.flow.Flow

@Dao
interface ComplexDao {

    @Transaction
    @Query("SELECT * FROM user")
    fun getUserWithVisitsFlow(): Flow<List<UserWithVisits>>

    @Transaction
    @Query("SELECT * FROM place")
    fun getPlaceWithVisitsFlow(): Flow<List<PlaceWithVisits>>
    @Transaction
    @Query("SELECT * FROM place")
    fun getPlaceWithVisitorFlow(): Flow<List<PlaceWithVisitor>>

    @Transaction
    @Query("SELECT * FROM place")
    fun getPlaceWithVisitorPagingSource(): PagingSource<Int, PlaceWithVisitor>
}