package com.example.todo.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

// TODO add validation
// TODO add reference to Account
@Entity
data class Todo (
        @Id
        @GeneratedValue
        var id: Long? = null,
        var title: String? = null,
        var isDone: Boolean = false
)