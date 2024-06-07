package com.example.empty

import androidx.test.core.app.ApplicationProvider
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PlaceRepoTest {

    @Test
    fun test() {
        val repo = CountryRepoImpl(ApplicationProvider.getApplicationContext())
        assert(repo.countries().isNotEmpty())
    }
}