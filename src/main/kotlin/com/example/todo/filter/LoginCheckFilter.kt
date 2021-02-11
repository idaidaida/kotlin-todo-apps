package com.example.todo.filter

import javax.servlet.Filter;
import com.example.todo.session.LoginSession
import org.springframework.stereotype.Component
import templates.extention.containsWithRegex
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class LoginCheckFilter(val loginSession: LoginSession): Filter {

    // url paths that are allowed to access without login
    val allowAccessPathWithoutLogins: MutableList<String> = mutableListOf<String>(
            "/login",
            "/",
            "/accounts",
            "/accounts/new",
            "/accounts/provisionalRegist",
            "/accounts/create/.*",
            "/vender/.*",
            "/original/.*"
    )

    override fun init(filterConfig: FilterConfig){
        //Filter.super.init(filterConfig)
    }

    override fun destroy(){
        //Filter.super.init(filterConfig)
    }

    // check if url is accessible without login.
    override fun doFilter(request: ServletRequest,response: ServletResponse,chain: FilterChain){
        // get URI from URL
        val httpReq = request as HttpServletRequest
        val requestUri = httpReq.getRequestURI()
        // check if match or not....
        if (requestUri.containsWithRegex(allowAccessPathWithoutLogins)) {
            chain.doFilter(httpReq, response)
        } else {
            if (loginSession.isLogined) {
                chain.doFilter(httpReq, response)
            } else {
                val httpRes = response as HttpServletResponse
                httpRes.sendRedirect("/login")
            }
        }
    }
}