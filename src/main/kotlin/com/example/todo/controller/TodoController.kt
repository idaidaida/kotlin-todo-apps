package com.example.todo.controller

import com.example.todo.model.Todo
import com.example.todo.repository.TodoRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class TodoController(val todoRepository: TodoRepository) {

    @GetMapping("/todos")
    fun index(model: Model): String{
        // Todo Delete after....
        val todo = Todo()
        todo.title = "first task"
        todo.isDone = true
        todoRepository.save(todo)

        val todos = todoRepository.findAll()

        model.addAttribute("todos",todos)
        return "todo/index"
    }
}