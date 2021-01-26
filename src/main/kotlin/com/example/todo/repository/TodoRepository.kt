package com.example.todo.repository

import com.example.todo.model.Account
import com.example.todo.model.Todo
import org.springframework.data.repository.CrudRepository

interface TodoRepository: CrudRepository<Todo, Long> {
    fun findByCreatedBy(account: Account): List<Todo>
}
