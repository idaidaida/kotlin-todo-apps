package com.example.todo

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController {

    @GetMapping("/helloWorld")
    fun helloWorld(): String{
        return "helloWorld"
    }

}