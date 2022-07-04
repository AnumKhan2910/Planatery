package com.adyen.android.assignment.utils

import android.view.View

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.updateViewVisibility(visible: Boolean) {
    when {
        visible -> show()
        else -> hide()
    }
}