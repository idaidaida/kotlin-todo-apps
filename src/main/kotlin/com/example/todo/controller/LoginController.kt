package com.example.todo.controller

import com.example.todo.repository.AccountRepository
import com.example.todo.session.LoginSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import templates.extention.generateHashValue
import javax.validation.constraints.NotBlank

@RequestMapping("/login")
@Controller
class LoginController(val accountRepository: AccountRepository,val loginSession: LoginSession) {

    @GetMapping("")
    fun new(model: Model): String{
        val loginForm = LoginForm()
        model.addAttribute("loginForm",loginForm)
        return "login/new"
    }

    @PostMapping("")
    fun create(@ModelAttribute @Validated loginForm: LoginForm, result: BindingResult, model: Model,redirectModel: RedirectAttributes): String{
        // check input value
        if (result.hasErrors()) {
            return "/login/new"
        }
        // password authentication
        // password is stored as hash value
        val account = accountRepository.findByNameAndPassword(loginForm.name,loginForm.password.generateHashValue())
        if(account.size == 1){
            // check mail verification
            if(!account[0].isVerifiedMailAddress){
                model.addAttribute("error_message","your mail address is not verified yet. please check your mailbox, and click link on mail body to verify your mail address")
                return "/login"
            }
            // login
            loginSession.create(account[0])
        }else{
            model.addAttribute("error_message","name or password is wrong")
            return "/login"
        }
        // redirect to todo list
        redirectModel.addFlashAttribute("flush_info_message", "Login successfully.");
        return "redirect:/todos"
    }

    @GetMapping("/delete")
    fun delete(redirectModel: RedirectAttributes): String{
        // logout
        loginSession.destroy()
        redirectModel.addFlashAttribute("flush_info_message", "Logout successfully.");
        return "redirect:/login"
    }

    // login form
    data class LoginForm(
            @field:NotBlank
            val name: String = "",
            @field:NotBlank
            val password: String = ""
    )

}

