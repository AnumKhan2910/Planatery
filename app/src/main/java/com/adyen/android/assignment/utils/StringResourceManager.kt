package com.adyen.android.assignment.utils

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface StringResourceManager {
    fun getString(@StringRes id: Int): String
}

class DefaultStringResourceManager @Inject constructor(
    @ApplicationContext private var context: Context
): StringResourceManager {

    override fun getString(@StringRes id: Int) = context.getString(id)
}