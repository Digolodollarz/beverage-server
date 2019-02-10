package tech.diggle.apps.qikpay

import com.fasterxml.jackson.databind.util.StdConverter
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class DateTo8601 : StdConverter<Date, String>() {
    override fun convert(date: Date): String {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).format(DateTimeFormatter.ISO_DATE_TIME)
    }
}