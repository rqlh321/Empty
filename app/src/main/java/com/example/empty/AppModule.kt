package com.example.empty

import android.content.Context
import androidx.room.Room
import com.example.empty.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface AppModule {
    companion object {

        @Provides
        fun providesDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "database-name"
        ).build()
    }
}