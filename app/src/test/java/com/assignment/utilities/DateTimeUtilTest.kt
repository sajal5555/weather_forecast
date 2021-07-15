package com.assignment.utilities

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtilTest {
    private var currentTime: Long = 0

    @Before
    fun setUp() {
        currentTime = System.currentTimeMillis() / 1000

    }

    @Test
    fun getDate() {
        val actual = DateTimeUtil().getDate(currentTime.toInt())

        val currentDate = Date(currentTime * 1000)
        val df = SimpleDateFormat("E, dd.MM.yyyy - hh:mm a", Locale.getDefault())
        val expected = df.format(currentDate)

        MatcherAssert.assertThat(expected, CoreMatchers.`is`(actual))
    }

    @Test
    fun getDay() {
        val actual = DateTimeUtil().getDay(currentTime.toInt())

        val currentDate = Date(currentTime * 1000)
        val df = SimpleDateFormat("dd", Locale.getDefault())
        val expected = df.format(currentDate)

        MatcherAssert.assertThat(expected, CoreMatchers.`is`(actual))
    }

    @After
    fun tearDown() {
    }
}