package com.example.todo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.scheduling.annotation.EnableAsync


@EnableAsync
@SpringBootApplication
class TodoAppsApplication: SpringBootServletInitializer()

fun main(args: Array<String>) {
	runApplication<TodoAppsApplication>(*args)
}
