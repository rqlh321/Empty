package com.example.empty

import android.app.Application
import com.example.empty.database.AppDatabase
import com.example.empty.database.entity.AppMetadata
import com.example.empty.database.entity.Place
import com.example.empty.database.entity.User
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var countryRepo: CountryRepo

    @Inject
    lateinit var appDatabase: AppDatabase

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            val metadata = appDatabase.metadataDao().get().firstOrNull()

            if (metadata == null || !metadata.isSetupFinish) {
                val countries = countryRepo.countries()
                    .map { Place(UUID.randomUUID().toString(), it) }
                appDatabase.countryDao().insert(countries)

                val anonId = UUID.randomUUID().toString()
                val anon = User(anonId, "Anon")
                appDatabase.userDao().insert(anon)

                val appMetadata = AppMetadata(isSetupFinish = true, currentUserId = anonId)
                appDatabase.metadataDao().upsert(appMetadata)
            }
        }
    }
}