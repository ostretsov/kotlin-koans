package iii_conventions

import com.sun.org.apache.xpath.internal.operations.Bool
import iv_properties.toMillis

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        return (this.toMillis()/1000).toInt() - (other.toMillis()/1000).toInt()
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> = object : Iterator<MyDate> {
        var current = start

        override fun next(): MyDate {
            val result = current
            current = current.nextDay()

            return result
        }

        override fun hasNext() = current <= endInclusive
    }

    operator fun contains(date: MyDate): Boolean = start <= date && date <= endInclusive
}
