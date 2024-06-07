package com.example.empty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.empty.database.AppDatabase
import com.example.empty.database.entity.Visit
import com.example.empty.database.related.PlaceWithVisitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PlacesContentViewModel @Inject constructor(
    private val appDatabase: AppDatabase,
) : ViewModel() {

    private val mutableStateFlow = MutableStateFlow(emptyList<PlaceWithVisitor>())
    val stateFlow = mutableStateFlow.asStateFlow()

    init {
        appDatabase.complexDao()
            .getPlaceWithVisitorFlow()
            .distinctUntilChanged()
            .onEach { mutableStateFlow.emit(it) }
            .launchIn(viewModelScope)
    }

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