package com.example.compassapp

import com.example.compassapp.utils.areCoordinatesValuesCorrect
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized


@RunWith(Parameterized::class)
class LogicUtilTest(
    private val expected: Boolean,
    private val latitude: String,
    private val longitude: String,
    private val scenario: String
) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "areCoordinatesValuesCorrect: {3}")
        fun coordinates() = listOf(
            arrayOf(true, "0", "0", "Both correct, true"),
            arrayOf(false, "", "", "Both invalid, false"),
            arrayOf(false, "20.0021", "181.21", "Longitude invalid, false"),
            arrayOf(false, "-91", "140", "Latitude invalid, false"),
            arrayOf(false, "-100", "190.323", "Both invalid, false")
        )
    }

    @Test
    fun test_areCoordinatesValuesCorrectEmpty() {
        val actual = areCoordinatesValuesCorrect(latitude, longitude)
        assertEquals(expected, actual)
    }
}

