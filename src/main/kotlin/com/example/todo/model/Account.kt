package com.example.todo.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

// TODO add validation
@Entity
data class Account (
        @Id
        @GeneratedValue
        var id: Long? = null,
        var name: String? = null,
        var mail: String? = null,
        var password: String? = null,
        var isDefinitiveRegistration: Boolean = false
)