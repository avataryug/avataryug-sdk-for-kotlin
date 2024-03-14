package com.Avataryug.client.Infrastructure

import android.os.Build
import androidx.annotation.RequiresApi
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateAdapter {
    @RequiresApi(Build.VERSION_CODES.O)
    @ToJson
    fun toJson(value: LocalDate): String {
        return DateTimeFormatter.ISO_LOCAL_DATE.format(value)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @FromJson
    fun fromJson(value: String): LocalDate {
        return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE)
    }

}
