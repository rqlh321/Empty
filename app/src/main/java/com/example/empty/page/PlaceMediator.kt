package com.example.empty.page

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.empty.database.AppDatabase
import com.example.empty.database.entity.Place
import com.example.empty.database.related.PlaceWithVisitor
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Singleton
class PlaceMediator @Inject constructor(
    private val appDatabase: AppDatabase,
) : RemoteMediator<Int, PlaceWithVisitor>() {
    override suspend fun initialize() = InitializeAction.LAUNCH_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PlaceWithVisitor>
    ) = try {
        when (loadType) {
            LoadType.REFRESH -> {
                nextPart(state.config.initialLoadSize)
                MediatorResult.Success(false)
            }

            LoadType.PREPEND -> {
                nextPart(state.config.pageSize)
                MediatorResult.Success(false)
            }
            LoadType.APPEND -> {
                nextPart(state.config.pageSize)
                MediatorResult.Success(false)
            }
        }
    } catch (e: Exception) {
        MediatorResult.Error(e)
    }

    private suspend fun nextPart(limit: Int) {
        val places = (0..<limit)
            .map {
                val id = UUID.randomUUID().toString()
                Place(id, id)
            }
        appDatabase.countryDao().insert(places)
    }
}