package com.example.todo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
class TodoAppsApplication

fun main(args: Array<String>) {
	runApplication<TodoAppsApplication>(*args)
}
