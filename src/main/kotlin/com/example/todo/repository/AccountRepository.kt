package com.example.todo.repository

import com.example.todo.model.Account
import org.springframework.data.repository.CrudRepository

interface AccountRepository: CrudRepository<Account, Long> {
    fun findByNameAndPassword(name: String,password: String): List<Account>
    fun findByName(name: String): List<Account>
}
