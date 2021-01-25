package com.example.todo.controller

import com.example.todo.model.Account
import com.example.todo.repository.AccountRepository
import com.example.todo.service.SendMailService
import com.example.todo.session.LoginSession
import org.springframework.data.repository.findByIdOrNull
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import templates.extention.encript
import javax.validation.constraints.*

@RequestMapping("/accounts")
@Controller
class AccountController(val accountRepository: AccountRepository, val mailSender: MailSender, val sendMailService: SendMailService,val loginSession: LoginSession) {

    @GetMapping("/new")
    fun new(model: Model): String{
        val accountCreateForm = AccountController.AccountCreateForm()
        model.addAttribute("accountCreateForm",accountCreateForm)
        return "/account/new"
    }

    @PostMapping("/provisionalRegist")
    fun provisionalRegist(@ModelAttribute @Validated accountCreateForm: AccountCreateForm, result: BindingResult, model: Model): String{

        // 入力チェック
        if (result.hasErrors()) {
            return "/account/new"
        }else if(accountCreateForm.password != accountCreateForm.passwordConfirm){
            model.addAttribute("error_message","password and confirm_password are different")
            return "/account/new"
        }
        
        // TODO きれいにする
        val account = Account()
        account.name = accountCreateForm.name
        account.mail = accountCreateForm.mail
        account.password = accountCreateForm.password
        account.provisionRegistHashValue = (account.mail + account.name).encript()
        accountRepository.save(account)

        // TODO !!の意味が分からん
        sendMailService.sendProvisionRegisterMail(account.id!!, account.mail!!, account.provisionRegistHashValue!!)
        return "account/provisionalRegist"
    }

    @GetMapping("/create/{id}/{hashValue}")
    fun create(@PathVariable id: Long,@PathVariable hashValue: String,model: Model): String{
        val account: Account? = accountRepository.findByIdOrNull(id!!)
        if(account != null && account.provisionRegistHashValue == hashValue && !account.isDefinitiveRegistration){
            account.isDefinitiveRegistration = true
            accountRepository.save(account)
            model.addAttribute("isSuccess",true)
        }else{
            model.addAttribute("isSuccess",false)
        }

        return "account/create"
    }

    @PostMapping("/delete")
    fun destroy(redirectModel: RedirectAttributes): String{
        val account: Account? = loginSession.getLoginAccount()
        if(account != null && account.name == "guest"){
            redirectModel.addFlashAttribute("flush_danger_message", "You can't delete guest account.");
            return "redirect:/todos"
        }
        loginSession.destroy()
        return "redirect:/"
    }

    data class AccountCreateForm(
            @field:NotNull
            @Pattern(regexp = "^[a-zA-Z0-9_/-]*$")
            var name: String? = null,
            @field:NotNull
            var mail: String? = null,
            @field:NotNull
            @field:Size(min = 6, max=50)
            @Pattern(regexp = "^[a-zA-Z0-9_/-]*$")
            var password: String? = null,
            @Pattern(regexp = "^[a-zA-Z0-9_/-]*$")
            var passwordConfirm: String? = null,
    )


}