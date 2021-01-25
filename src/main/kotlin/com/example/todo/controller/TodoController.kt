package com.example.todo.controller

import com.example.todo.model.Todo
import com.example.todo.repository.TodoRepository
import com.example.todo.session.LoginSession
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@RequestMapping("/todos")
@Controller
class TodoController(val todoRepository: TodoRepository,val loginSession: LoginSession) {

    @GetMapping("")
    fun index(model: Model): String{
        val todos = todoRepository.findAll()
        model.addAttribute("todos",todos)

        val todo = Todo()
        model.addAttribute("todo",todo)
        return "todo/index"
    }

    @PostMapping("")
    fun create(@ModelAttribute @Validated todo: Todo, result: BindingResult, model: Model,redirectModel: RedirectAttributes): String {

        if (result.hasErrors()) {
            return "/todo/index"
        }

        todo.createdBy = loginSession.getLoginAccount()
        todoRepository.save(todo)
        redirectModel.addFlashAttribute("flush_info_message", "Create new todo successfully.");
        return "redirect:todos"
    }

    @PostMapping("/{id}/delete")
    fun destroy(@PathVariable id: Long,redirectModel: RedirectAttributes): String{
        // TODO CSRF対策必要じゃない？
        val todo = todoRepository.deleteById(id)
        redirectModel.addFlashAttribute("flush_info_message", "Delete todo successfully.");
        return "redirect:/todos"
    }

    @GetMapping("/{id}/edit")
    fun edit(@PathVariable id: Long,model: Model): String{
        val todo = todoRepository.findByIdOrNull(id)
        model.addAttribute("todo",todo)
        return "todo/edit"
    }

    @PostMapping("/{id}/update")
    fun update(@PathVariable id: Long, @ModelAttribute @Validated todo: Todo,result: BindingResult, redirectModel: RedirectAttributes): String{
        // TODO タイトルのみ書き替えるようにSQLをつくる
        todo.id = id
        todo.createdBy = loginSession.getLoginAccount()
        todoRepository.save(todo)
        redirectModel.addFlashAttribute("flush_info_message", "Update todo successfully.");
        return "redirect:/todos"
    }

}