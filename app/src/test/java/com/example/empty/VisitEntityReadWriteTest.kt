package com.example.empty

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.empty.database.AppDatabase
import com.example.empty.database.entity.Place
import com.example.empty.database.entity.User
import com.example.empty.database.entity.Visit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException
import java.util.UUID
import kotlin.random.Random

@RunWith(RobolectricTestRunner::class)
class VisitEntityReadWriteTest {
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        runBlocking {
            val metadata = db.metadataDao().get().firstOrNull()

            if (metadata == null || !metadata.isSetupFinish) {
                val places = context.resources
                    .getStringArray(R.array.countries_array)
                    .map { Place(UUID.randomUUID().toString(), it) }
                db.countryDao().insert(places)

                val users = (0..2).map { User(UUID.randomUUID().toString(), it.toString()) }
                db.userDao().insert(users)

                val visits = users.map { user ->
                    (0..2).map {
                        Visit(
                            id = UUID.randomUUID().toString(),
                            placeId = places.first().id,
                            userId = user.id,
                            timeStamp = Random.nextLong(0, System.currentTimeMillis())
                        )
                    }
                }.flatten()
                db.visitDao().insert(visits)
                db.metadataDao().upsert(
                    com.example.empty.database.entity.AppMetadata(
                        isSetupFinish = true,
                        currentUserId = users.first().id
                    )
                )
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
            val places = db.complexDao().getPlaceWithVisitorFlow().first()
            assert(places.isNotEmpty())
        }
    }
}