package com.adyen.android.assignment.utils

import android.content.Context
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ToastManager {
    fun show(message: String?, length: Int = Toast.LENGTH_SHORT)
}

class DefaultToastManager @Inject constructor(
    @ApplicationContext private val context: Context
) : ToastManager {

    override fun show(message: String?, length: Int) {
        if (message.isNullOrEmpty()) return
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_LONG
        ).show()
    }
}