package com.example.todo.service

import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.net.URLEncoder

@Service
class SendMailService(val mailSender: MailSender) {

    @Async
    fun sendProvisionRegisterMail(id: Long,to: String,hashValue: String){
        // TODO 非同期にする
        // TODO メールアドレスをyuuhei03110311じゃなくする
        val msg = SimpleMailMessage()
        msg.setFrom("no-replay@example.com")
        msg.setTo(to)
        msg.setSubject("【Todo Apps】 Confirm your e-mail address")
        val encodedTo = URLEncoder.encode(to,"UTF-8")
        msg.setText("Click below link to verification\n http://localhost:8080/accounts/create/${id}/${hashValue}")
        mailSender.send(msg)
    }
}