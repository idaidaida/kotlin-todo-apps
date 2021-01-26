package com.example.todo.model

import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Entity
data class Account (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        @field:NotBlank
        @field:Pattern(regexp = "^[a-zA-Z0-9_/-]*$")
        var name: String? = null,
        @field:NotBlank
        var mail: String? = null,
        @field:NotBlank
        @field:Size(min = 6, max=50)
        @field:Pattern(regexp = "^[a-zA-Z0-9_/-]*$")
        var password: String? = null,
        var isVerifiedMailAddress: Boolean = false,
        var personalHashValue: String? = null
)