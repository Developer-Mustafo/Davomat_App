package uz.coder.davomatapp.data.db

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate

class Converters {

    // LocalDate -> String
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.toString()  // ISO format yyyy-MM-dd
    }

    // String -> LocalDate
    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it) }
    }
}
