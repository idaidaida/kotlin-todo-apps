package com.example.todo.filter

import org.hibernate.validator.spi.messageinterpolation.LocaleResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.i18n.FixedLocaleResolver
import java.util.*

@Configuration
class LocalConfig {
    @Bean
    fun localeResolver(): FixedLocaleResolver {
        // set local English
        val fixedLocaleResolver = FixedLocaleResolver()
        fixedLocaleResolver.setDefaultLocale(Locale.ENGLISH)
        return fixedLocaleResolver
    }
}