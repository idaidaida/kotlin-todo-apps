package com.example.todo.session

import com.example.todo.model.Account
import com.example.todo.repository.AccountRepository
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.io.Serializable

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
class LoginSession(val accountRepository: AccountRepository): Serializable {
    var isLogined: Boolean = false
    var accountId: Long? = null

    private val serialVersionUID = 1L

    fun create(account: Account){
        this.isLogined = true
        this.accountId = account.id
    }

    fun getLoginAccountId(): Long?{
        return this.accountId
    }

    fun getLoginAccount(): Account?{
        return accountRepository.findByIdOrNull(this.accountId!!)
    }

    fun destroy(){
        this.isLogined = false
        this.accountId = null
    }
}