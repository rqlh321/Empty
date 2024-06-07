package com.example.empty.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.empty.database.entity.AppMetadata
import kotlinx.coroutines.flow.Flow

@Dao
interface MetadataDao : BaseDao<AppMetadata> {

    @Query("SELECT * FROM app_metadata")
    fun get(): Flow<AppMetadata>

}