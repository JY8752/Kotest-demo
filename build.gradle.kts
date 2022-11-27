val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.7.21"
    id("io.ktor.plugin") version "2.1.3"
}

group = "com.example"
version = "0.0.1"
application {
    mainClass.set("com.example.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    // kotest
    val kotest_version: String by project
    val kotest_assertions_ktor_version: String by project
    testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotest_version")
    testImplementation("io.kotest:kotest-property:$kotest_version")
    testImplementation("io.kotest:kotest-framework-datatest:$kotest_version")
    testImplementation("io.kotest.extensions:kotest-assertions-ktor:$kotest_assertions_ktor_version")

    // mockk
    testImplementation("io.mockk:mockk:1.13.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}