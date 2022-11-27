package com.example.property

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.string.shouldHaveLength
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.checkAll
import io.kotest.property.forAll

internal class PropertyExample : StringSpec({
    "String size" {
        forAll<String, String> { a, b ->
            (a + b).length == a.length + b.length
        }
    }
    "String size2" {
        checkAll<String, String>(10_000) { a, b ->
            a + b shouldHaveLength a.length + b.length
        }
    }
    "is allowed to drink" {
        forAll(Arb.int(20..150)) { a ->
            a >= 20
        }
    }
})
