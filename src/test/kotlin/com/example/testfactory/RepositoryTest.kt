package com.example.testfactory

import io.kotest.core.spec.style.StringSpec
import io.kotest.core.spec.style.stringSpec
import io.kotest.matchers.shouldBe

fun <T> repositoryTests(name: String, repository: CrudRepository<T>, entity: T) = stringSpec {
    name {
        val saved = repository.save(entity)
        val find = repository.findById(1)
        saved shouldBe find
    }
}

internal class RepositoryTestSuite : StringSpec({
    val userRepository = UserRepositoryImpl()
    val groupRepository = GroupRepositoryImpl()
//    "save and find" {
//        val saved = repository.save(UserEntity(1, "user"))
//        val find = repository.findById(1)
//        saved shouldBe find
//    }
    include(repositoryTests("UserRepository: save and find", userRepository, UserEntity(1, "user")))
    include(repositoryTests("GroupRepositoryTest: save and find", groupRepository, GroupEntity(1, "GroupA")))
})
