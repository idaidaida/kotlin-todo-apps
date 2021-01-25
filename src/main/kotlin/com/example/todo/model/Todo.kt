package com.example.todo.model

import org.jetbrains.annotations.NotNull
import javax.persistence.*
import javax.validation.constraints.NotBlank

// TODO add validation
// TODO add reference to Account
@Entity
data class Todo (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        @NotBlank
        var title: String? = null,
        var isDone: Boolean? = false,
        @OneToOne
        var createdBy: Account? = null
)