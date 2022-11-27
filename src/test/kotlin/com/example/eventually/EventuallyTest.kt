package com.example.eventually

import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.framework.concurrency.eventually
import io.kotest.matchers.shouldBe
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalKotest::class)
internal class EventuallyTest : FunSpec({
    test("test") {
        var str = "x"
        eventually({
            duration = 10000
            listener = { println("iteration ${it.times} returned ${it.result}") }
        }) {
            str += "x"
            str
        }
    }
})