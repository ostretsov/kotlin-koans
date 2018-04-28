package iii_conventions

import iv_properties.toMillis

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        return (this.toMillis()/1000).toInt() - (other.toMillis()/1000).toInt()
    }

    operator fun rangeTo(other: MyDate): DateRange = DateRange(this, other)
    operator fun plus(interval: TimeInterval): MyDate = addTimeIntervals(interval, 1)
    operator fun plus(multipliedTimeInterval: MultipliedTimeInterval): MyDate = addTimeIntervals(multipliedTimeInterval.interval, multipliedTimeInterval.factor)
}

data class MultipliedTimeInterval(val interval: TimeInterval, val factor: Int)
operator fun TimeInterval.times(i: Int): MultipliedTimeInterval = MultipliedTimeInterval(this, i)

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
