package com.adyen.android.assignment.data.network

import androidx.annotation.NonNull
import androidx.room.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
@Entity(tableName = "astronomyPictures")
data class AstronomyPicture(
    @NonNull
    @ColumnInfo(name = "title")
    val title: String = "",

    @NonNull
    @ColumnInfo(name = "desc")
    val explanation: String = "",

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "date")
    val date: String = "",

    @NonNull
    @Json(name = "hdurl")
    @ColumnInfo(name = "url")
    val hdUrl: String? = "",

    @NonNull
    @ColumnInfo(name = "favourite")
    val favourite: Boolean = false,

    val isTitle: Boolean = false
): Serializable