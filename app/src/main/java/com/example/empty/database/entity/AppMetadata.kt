package com.example.empty.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "app_metadata",
    primaryKeys = ["id"]
)
data class AppMetadata(
    @ColumnInfo(name = "is_setup_finish") val isSetupFinish: Boolean,
    @ColumnInfo(name = "id") val id: String = METADATA_ID,
    @ColumnInfo(name = "current_user_id") val currentUserId: String,
) {

    companion object {
        const val METADATA_ID = "app_metadata"
    }
}