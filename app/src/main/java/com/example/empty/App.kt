package com.example.empty

import android.app.Application
import com.example.empty.database.AppDatabase
import com.example.empty.database.entity.AppMetadata
import com.example.empty.database.entity.User
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.UUID
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var appDatabase: AppDatabase

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.Default).launch {
            val metadata = appDatabase.metadataDao().get().firstOrNull()

            if (metadata == null) {
                val anonId = UUID.randomUUID().toString()
                val anon = User(anonId, "Anon")
                appDatabase.userDao().insert(anon)

                val appMetadata = AppMetadata(currentUserId = anonId)
                appDatabase.metadataDao().upsert(appMetadata)
            }
        }
    }
}