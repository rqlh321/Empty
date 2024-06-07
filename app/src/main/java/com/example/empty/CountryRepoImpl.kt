package com.example.empty

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CountryRepoImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : CountryRepo {

    override fun countries(): Array<String> =
        context.resources.getStringArray(R.array.countries_array)

}