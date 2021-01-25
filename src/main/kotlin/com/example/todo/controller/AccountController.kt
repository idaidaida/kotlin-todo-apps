package com.example.todo.controller

import com.example.todo.model.Account
import com.example.todo.repository.AccountRepository
import com.example.todo.service.SendMailService
import com.example.todo.session.LoginSession
import org.springframework.data.repository.findByIdOrNull
import org.springframework.mail.MailSender
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import templates.extention.generateHashValue
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
        // check input value
        if (result.hasErrors()) {
            return "/account/new"
        }else if(accountCreateForm.password != accountCreateForm.passwordConfirm){
            model.addAttribute("error_message","password and confirm_password are different")
            return "/account/new"
        }
        // insert account data
        // personalHashValue is used to identify user when verifying account mail address
        val account = Account()
        account.name = accountCreateForm.name
        account.mail = accountCreateForm.mail
        account.password = accountCreateForm.password?.generateHashValue() // password is encripted before insert
        account.personalHashValue = (account.mail + account.name).generateHashValue()
        accountRepository.save(account)
        // send email to verify mail address
        sendMailService.sendProvisionRegisterMail(account.id!!, account.mail!!, account.personalHashValue!!)
        return "account/provisionalRegist"
    }

    @GetMapping("/create/{id}/{urlHashValue}")
    fun create(@PathVariable id: Long,@PathVariable urlHashValue: String,model: Model): String{
        // select account data
        val account: Account? = accountRepository.findByIdOrNull(id)
        // check hashValue
        if(account != null && !account.isVerifiedMailAddress && account.personalHashValue == urlHashValue ){
            // update verify status
            account.isVerifiedMailAddress = true
            accountRepository.save(account)
            model.addAttribute("isSuccess",true)
        }else{
            // when somthing is wrong...
            model.addAttribute("isSuccess",false)
        }
        return "account/create"
    }

    @PostMapping("/delete")
    fun destroy(redirectModel: RedirectAttributes): String{
        // get login account info
        val account: Account? = loginSession.getLoginAccount()
        // guest user must not be delete
        if(account != null && account.name == "guest"){
            redirectModel.addFlashAttribute("flush_danger_message", "You can't delete guest account.");
            return "redirect:/todos"
        }
        // logout
        loginSession.destroy()
        return "redirect:/"
    }

    // account create form
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