package com.example.empty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.empty.database.AppDatabase
import com.example.empty.database.entity.Visit
import com.example.empty.database.related.PlaceWithVisitor
import com.example.empty.page.PlaceMediator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PlacesContentViewModel @Inject constructor(
    private val appDatabase: AppDatabase,
    private val placeMediator: PlaceMediator,
) : ViewModel() {


    @OptIn(ExperimentalPagingApi::class)
    val stateFlow = Pager(
        config = PagingConfig(50),
        remoteMediator = placeMediator,
        pagingSourceFactory = { appDatabase.complexDao().getPlaceWithVisitorPagingSource() }
    ).flow.cachedIn(viewModelScope)

    fun onItemClick(item: PlaceWithVisitor) {
        viewModelScope.launch {
            appDatabase.visitDao().insert(
                Visit(
                    id = UUID.randomUUID().toString(),
                    placeId = item.place.id,
                    userId = appDatabase.metadataDao().get().first().currentUserId,
                    timeStamp = System.currentTimeMillis(),
                )
            )
        }
    }
}