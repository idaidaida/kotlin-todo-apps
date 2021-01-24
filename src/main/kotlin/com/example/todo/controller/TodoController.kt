package com.example.todo.controller

import com.example.todo.model.Todo
import com.example.todo.repository.TodoRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@RequestMapping("/todos")
@Controller
class TodoController(val todoRepository: TodoRepository) {

    @GetMapping("")
    fun index(model: Model): String{
        // Todo Delete after....
        val todos = todoRepository.findAll()
        model.addAttribute("todos",todos)
        return "todo/index"
    }

    @PostMapping("")
    fun create(@RequestParam title: String): String {
        // TODO add validation

        val todo = Todo()
        todo.isDone = true
        todo.title = title
        todoRepository.save(todo)
        return "redirect:todos"
    }

}