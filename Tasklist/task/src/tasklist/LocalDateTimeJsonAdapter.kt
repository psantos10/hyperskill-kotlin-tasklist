package tasklist

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime

class LocalDateTimeJsonAdapter {
    @ToJson
    fun toJson(dateTime: LocalDateTime): String {
        return dateTime.toString()
    }

    @FromJson
    fun fromJson(dateTimeString: String): LocalDateTime {
        return dateTimeString.toLocalDateTime()
    }
}