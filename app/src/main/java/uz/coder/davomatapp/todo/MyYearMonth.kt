package uz.coder.davomatapp.todo

import kotlinx.datetime.LocalDate

data class MyYearMonth(val year: Int, val month: Int) : Comparable<MyYearMonth> {

    fun plusMonths(n: Int): MyYearMonth {
        val totalMonths = (year * 12 + (month - 1) + n)
        val newYear = totalMonths / 12
        val newMonth = totalMonths % 12 + 1
        return MyYearMonth(newYear, newMonth)
    }

    fun lengthOfMonth(): Int {
        val firstDay = LocalDate(year, month, 1)
        val nextMonth = if (month == 12) LocalDate(year + 1, 1, 1) else LocalDate(year, month + 1, 1)
        return (nextMonth.toEpochDays() - firstDay.toEpochDays()).toInt()
    }

    override fun compareTo(other: MyYearMonth): Int =
        (year * 12 + month).compareTo(other.year * 12 + other.month)
}