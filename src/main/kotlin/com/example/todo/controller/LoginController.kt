package com.example.todo.controller

import com.example.todo.repository.AccountRepository
import com.example.todo.session.LoginSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

@RequestMapping("/login")
@Controller
class LoginController(val accountRepository: AccountRepository,val loginSession: LoginSession) {

    @GetMapping("")
    fun new(model: Model): String{
        // ログインフォームを表示
        val loginForm = LoginForm()
        model.addAttribute("loginForm",loginForm)
        return "login/new"
    }

    @PostMapping("")
    fun create(@ModelAttribute @Validated loginForm: LoginForm, result: BindingResult, model: Model,redirectModel: RedirectAttributes): String{

        // 入力チェック
        if (result.hasErrors()) {
            return "/login/new"
        }

        // TODO パスワードは暗号化
        // TODO 狩り登録は除外
        // TODO ログインチェックをFilterに移す
        val account = accountRepository.findByNameAndPassword(loginForm.name,loginForm.password)
        if(account.size == 1){
            loginSession.create(account[0])
            println("Login Success")
        }else{
            println("Login Failed")
        }
        redirectModel.addFlashAttribute("flush_info_message", "Login successfully.");
        return "redirect:/todos"
    }

    @GetMapping("/delete")
    fun delete(redirectModel: RedirectAttributes): String{
        loginSession.destroy()
        redirectModel.addFlashAttribute("flush_info_message", "Logout successfully.");
        return "redirect:/login"
    }

    data class LoginForm(
            @field:NotBlank
            val name: String = "",
            @field:NotEmpty
            val password: String = ""
    )

}

