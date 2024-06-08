package com.example.empty

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.empty.database.related.PlaceWithVisitor
import io.sentry.compose.SentryTraced
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
fun PlacesScreen() = SentryTraced("places_screen") {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { PlacesTopBar(scrollBehavior) },
        content = {
            val viewModel = hiltViewModel<PlacesContentViewModel>()
            val lazyPagingItems = viewModel.stateFlow.collectAsLazyPagingItems()

            val sheetState = rememberModalBottomSheetState()
            val scope = rememberCoroutineScope()
            var selectedPlaceItem by remember { mutableStateOf<PlaceWithVisitor?>(null) }

            PlacesContent(Modifier.padding(it), lazyPagingItems, { selectedPlaceItem = it })
            selectedPlaceItem?.let {
                PlacesBottomSheet(
                    placeName = it.place.name,
                    onConfirm = {
                        scope.launch {
                            viewModel.onItemClick(it)
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                selectedPlaceItem = null
                            }
                        }
                    },
                    onDismissRequest = { selectedPlaceItem = null },
                    sheetState = sheetState
                )
            }
        }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun PlacesBottomSheet(
    placeName: String,
    onConfirm: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
    sheetState: SheetState,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        Button(onClick = onConfirm) {
            Text("Visit $placeName!")
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun PlacesContent(
    modifier: Modifier = Modifier,
    items: LazyPagingItems<PlaceWithVisitor>,
    onItemClick: (PlaceWithVisitor) -> Unit = {}
) = SentryTraced("places_content") {
    LazyColumn(modifier = modifier) {
        items(
            count = items.itemCount,
            key = items.itemKey { it.place.id },
            contentType = items.itemContentType { "place_list_item" }
        ) {
            items[it]?.let {
                ListItem(
                    modifier = Modifier
                        .testTag("place_list_item")
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(color = MaterialTheme.colorScheme.onBackground),
                            onClick = { onItemClick(it) }
                        ),
                    headlineContent = { Text(text = it.place.name) },
                    supportingContent = { Text(text = it.visit.size.toString()) }
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
private fun PlacesTopBar(
    scrollBehavior: TopAppBarScrollBehavior
) = SentryTraced("places_top_bar") {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                "Medium Top App Bar",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },

        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Localized description"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}