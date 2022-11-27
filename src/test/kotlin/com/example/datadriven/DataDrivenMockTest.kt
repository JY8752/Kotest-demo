package com.example.datadriven

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime

data class UserEntity(val Id: Long? = null, val name: String, val email: String, val age: Int)
data class UserHistory(val Id: Long? = null, val userId: Long, val createdAt: LocalDateTime)

interface UserRepository {
    fun save(user: UserEntity): UserEntity
}

interface UserHistoryRepository {
    fun save(userHistory: UserHistory): UserHistory
}

class UserService(
    private val userRepository: UserRepository,
    private val userHistoryRepository: UserHistoryRepository
) {
    fun create(name: String, email: String, age: Int): User {
        val created = this.userRepository.save(UserEntity(name = name, email = email, age = age))
        this.userHistoryRepository.save(UserHistory(userId = created.Id!!, createdAt = LocalDateTime.now()))
        return created.toModel()
    }

    private fun UserEntity.toModel() = User(this.name, this.email, this.age)
}

data class User(val name: String, val email: String, val age: Int)

internal class DataDrivenMockTest : FunSpec({
    context("test UserService: create") {
        data class TestPattern(
            val userRepositoryMock: UserRepository,
            val userHistoryRepositoryMock: UserHistoryRepository,
            val argsName: String,
            val argsEmail: String,
            val argsAge: Int,
            val expect: User
        )
        withData(
            mapOf(
                "create user" to TestPattern(
                    object : UserRepository {
                        override fun save(user: UserEntity): UserEntity {
                            return UserEntity(1L, user.name, user.email, user.age)
                        }
                    },
                    object : UserHistoryRepository {
                        override fun save(userHistory: UserHistory): UserHistory {
                            return UserHistory(1L, userHistory.userId, userHistory.createdAt)
                        }
                    },
                    "user",
                    "test@test.com",
                    32,
                    User("user", "test@test.com", 32)
                )
            )
        ) { (userRepositoryMock, userHistoryRepositoryMock, name, email, age, expect) ->
            val service = UserService(userRepositoryMock, userHistoryRepositoryMock)
            service.create(name, email, age) shouldBe expect
        }
    }
})

class HelloComponent {
    fun getMessage(language: String): String {
        // なんかDB接続したり色々処理がある想定
        return ""
    }
}

class HelloService(private val helloComponent: HelloComponent) {
    fun hello(language: String): String {
        if (language.isEmpty()) throw RuntimeException("ちょっと何言ってるかわからないです。")
        return this.helloComponent.getMessage(language) + " Data Driven Testing!!"
    }
}

internal class HelloServiceTest : FunSpec({
    val mock = mockk<HelloComponent>()
    val service = HelloService(mock)
    context("test hello") {
        data class TestPattern(val language: String, val message: String, val expect: String? = null, val isError: Boolean = false)
        withData(
            mapOf(
                "language is English" to TestPattern("en", "Hello!!", "Hello!! Data Driven Testing!!"),
                "language is Japanese" to TestPattern("ja", "こんにちは!!", "こんにちは!! Data Driven Testing!!"),
                "language is Chinese" to TestPattern("zh", "ニーハオ!!", "ニーハオ!! Data Driven Testing!!"),
                "language is Unknown" to TestPattern("", "ちょっと何言ってるかわからないです。", isError = true)
            )
        ) { (language, message, expect, isError) ->
            every { mock.getMessage(language) } returns message
            if (isError) {
                shouldThrow<RuntimeException> { service.hello(language) }.message shouldBe message
            } else {
                service.hello(language) shouldBe expect
            }
        }
    }
})
