package com.example.todo.controller

import com.example.todo.model.Account
import com.example.todo.repository.AccountRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@RequestMapping("/accounts")
@Controller
class AccountController(val accountRepository: AccountRepository) {

    @GetMapping("")
    fun new(model: Model): String{
        val accounts = accountRepository.findAll()
        println(accounts)
        val account = Account()
        model.addAttribute("account",account)
        return "/account/new"
    }

    @PostMapping("/provisionalRegist")
    fun provisionalRegist(@ModelAttribute account: Account,model: Model,@RequestParam confirm_password: String): String{
        accountRepository.save(account)
        return "account/temporarycreate"
    }

    @PostMapping("")
    fun create
}