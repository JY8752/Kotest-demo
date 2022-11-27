package com.example.testfactory

interface UserRepository<T> : CrudRepository<T>

data class UserEntity(val id: Long? = null, val name: String)

class UserRepositoryImpl : UserRepository<UserEntity> {
    override fun findById(id: Long): UserEntity? {
        return UserEntity(id, "user")
    }

    override fun save(entity: UserEntity): UserEntity {
        return entity
    }
}
