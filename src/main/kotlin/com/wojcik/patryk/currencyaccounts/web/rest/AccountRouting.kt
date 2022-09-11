package com.wojcik.patryk.currencyaccounts.web.rest

import com.wojcik.patryk.currencyaccounts.web.rest.handler.AccountDownloadHandler
import com.wojcik.patryk.currencyaccounts.web.rest.handler.AccountExchangeHandler
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
    fun route(
        registerHandler: AccountRegisterHandler,
        accountHandler: AccountDownloadHandler,
        exchangeHandler: AccountExchangeHandler
    ) = router {
        POST("$URL/register", registerHandler::register)
        GET("$URL/{personalId}", accountHandler::getAccount)
        PATCH("$URL/exchange", exchangeHandler::exchange)
    }
}