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

    val allowAccessPathWithoutLogins: MutableList<String> = mutableListOf<String>(
            "/login",
            "/",
            "/accounts",
            "/accounts/new",
            "/accounts/provisionalRegist",
            "/accounts/create/.*"
    )

    override fun doFilter(request: ServletRequest,response: ServletResponse,chain: FilterChain){

        // リクエストからURIを取得
        val httpReq = request as HttpServletRequest
        val requestUri = httpReq.getRequestURI()

        if (requestUri.containsWithRegex(allowAccessPathWithoutLogins)) {
            chain.doFilter(httpReq, response)
        } else {
            if (loginSession.isLogined) {
                // ビジネスロジックへ処理を委譲
                chain.doFilter(httpReq, response)
            } else {
                // ログイン画面へリダイレクト
                val httpRes = response as HttpServletResponse
                httpRes.sendRedirect("/login")
            }
        }
    }
}