package com.example.datadriven

import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.datatest.WithDataTestName
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

internal class Test : StringSpec() {
    init {
        "test" {
            1 + 1 shouldBe 2
        }
    }
}

enum class Operator {
    ADD, SUBTRACTION, MULTIPLICATION, DIVIDE
}

fun calculate(num1: Int, num2: Int, operator: Operator): Int {
    return when (operator) {
        Operator.ADD -> num1 + num2
        Operator.SUBTRACTION -> num1 - num2
        Operator.MULTIPLICATION -> num1 * num2
        else -> num1 / num2
    }
}

internal class DataDrivenTest : FunSpec({
    context("test calculate") {
        data class TestPattern(val num1: Int, val num2: Int, val operator: Operator, val result: Int)
        withData(
            mapOf(
                "1 + 1 = 2" to TestPattern(1, 1, Operator.ADD, 2),
                "3 - 1 = 2" to TestPattern(3, 1, Operator.SUBTRACTION, 2),
                "2 x 3 = 6" to TestPattern(2, 3, Operator.MULTIPLICATION, 6),
                "10 / 5 = 2" to TestPattern(10, 5, Operator.DIVIDE, 2)
            )
        ) { (num1, num2, operator, result) ->
            calculate(num1, num2, operator) shouldBe result
        }
    }
})
