package com.example.todo.service

import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.net.URLEncoder

@Service
class SendMailService(val mailSender: MailSender) {

    // send mail is very slow
    // so i use Async
    @Async
    fun sendProvisionRegisterMail(id: Long,to: String,hashValue: String){
        val msg = SimpleMailMessage()
        msg.setFrom("no-replay@example.com")
        msg.setTo(to)
        msg.setSubject("【Todo Apps】 Confirm your e-mail address")
        val encodedTo = URLEncoder.encode(to,"UTF-8")
        msg.setText("Click below link to verification\n https://just-todo-apps.herokuapp.com/accounts/create/${id}/${hashValue}")
        mailSender.send(msg)
    }
}