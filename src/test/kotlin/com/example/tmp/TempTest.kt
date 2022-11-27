package com.example.tmp

import io.kotest.core.spec.style.FunSpec
import io.kotest.engine.spec.tempdir
import io.kotest.engine.spec.tempfile
import io.kotest.matchers.shouldBe

internal class TempTest : FunSpec({
    test("tempFile") {
        val file = tempfile()
        file.writeText("test")
        file.readText() shouldBe "test"
    }
    test("tempDir") {
        val dir = tempdir()
        dir.isDirectory shouldBe true
    }
})