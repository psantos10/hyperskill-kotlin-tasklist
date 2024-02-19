package tasklist

import kotlinx.datetime.*

class Task(var priority: Char, var dateTime: LocalDateTime, var lines: List<String>) {
    fun dueTag(): Char {
        return getDueTag()
    }

    fun flattenLines(): List<String> {
        return lines.map { it.chunked(44) }.flatten()
    }

    private fun getDueTag(): Char {
        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.of("UTC+0")).date
        val numberOfDays = currentDate.daysUntil(dateTime.date)

        return if (numberOfDays > 0) {
            'I'
        } else if (numberOfDays < 0) {
            'O'
        } else {
            'T'
        }
    }
}