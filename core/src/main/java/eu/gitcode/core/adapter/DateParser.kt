package eu.gitcode.core.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter


class DateParser {
    @FromJson
    fun fromJson(value: String): LocalDate {
        return FORMATTER.parse(value, LocalDate.FROM)
    }

    @ToJson
    fun toJson(value: LocalDate): String {
        return FORMATTER.format(value)
    }

    companion object {
        private val FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    }
}