package com.example.empty

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.empty.database.AppDatabase
import com.example.empty.database.entity.AppMetadata
import com.example.empty.database.entity.Place
import com.example.empty.database.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException
import java.util.UUID

@RunWith(RobolectricTestRunner::class)
class VisitEntityReadWriteTest {
    private lateinit var db: AppDatabase
    private val anonId = UUID.randomUUID().toString()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        runBlocking {
            val metadata = db.metadataDao().get().firstOrNull()

            if (metadata == null) {
                val anon = User(anonId, "Anon")
                db.userDao().insert(anon)

                val appMetadata = AppMetadata(currentUserId = anonId)
                db.metadataDao().upsert(appMetadata)
            }
        }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        runBlocking {
            db.complexDao().getPlaceWithVisitorFlow()
                .onEach {
                    assert(it.isNotEmpty())
                }
                .launchIn(CoroutineScope(currentCoroutineContext()))
            db.countryDao().insert(Place("1", "1"))
            delay(1000)
        }
    }
}