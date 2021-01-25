package com.example.todo.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

// TODO add validation
@Entity
data class Account (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        @field:NotNull
        @Pattern(regexp = "^[a-zA-Z0-9_/-]*$")
        var name: String? = null,
        @field:NotNull
        var mail: String? = null,
        @field:NotNull
        @field:Size(min = 6, max=50)
        @Pattern(regexp = "^[a-zA-Z0-9_/-]*$")
        var password: String? = null,
        var isDefinitiveRegistration: Boolean = false,
        var provisionRegistHashValue: String? = null
)