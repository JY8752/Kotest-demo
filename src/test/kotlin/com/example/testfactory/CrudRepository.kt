package com.example.testfactory

interface CrudRepository<T> {
    fun save(entity: T): T
    fun findById(id: Long): T?
}
