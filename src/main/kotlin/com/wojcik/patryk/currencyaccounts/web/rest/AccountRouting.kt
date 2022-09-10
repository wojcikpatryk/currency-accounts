package com.wojcik.patryk.currencyaccounts.web.rest

import com.wojcik.patryk.currencyaccounts.web.rest.handler.AccountRegisterHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class AccountRouting {

    companion object {
        private const val URL = "/api/accounts"
    }

    @Bean
    fun route(registerHandler: AccountRegisterHandler) = router {
        POST("$URL/register", registerHandler::register)
        TODO("Dodać EP do odczytu danego konta po przeliczeniu w NBP")
        TODO("Dodać EP do wymiany waluty po przeliczeniu w NBP")
    }
}