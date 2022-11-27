package com.example.testfactory

interface GroupRepository<T> : CrudRepository<T>

data class GroupEntity(val id: Long? = null, val name: String)

class GroupRepositoryImpl : GroupRepository<GroupEntity> {
    override fun findById(id: Long): GroupEntity? {
        return GroupEntity(id, "GroupA")
    }

    override fun save(entity: GroupEntity): GroupEntity {
        return entity
    }
}
