package com.example.todo.model

import org.jetbrains.annotations.NotNull
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
data class Todo (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        @field:NotBlank
        var title: String? = null,
        var isDone: Boolean? = false,
        @OneToOne
        var createdBy: Account? = null
)