package com.wojcik.patryk.currencyaccounts.web.rest.handler

import com.wojcik.patryk.currencyaccounts.core.exception.exchange.ExchangeException
import com.wojcik.patryk.currencyaccounts.domain.exchange.model.Exchange
import com.wojcik.patryk.currencyaccounts.domain.exchange.service.CurrencyExchangeService
import com.wojcik.patryk.currencyaccounts.web.rest.dto.error.ErrorMessage
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class AccountExchangeHandler(
    private val exchangeService: CurrencyExchangeService
) {

    fun exchange(request: ServerRequest) =
        request
            .bodyToMono(Exchange::class.java)
            .flatMap(exchangeService::exchange)
            .flatMap(ServerResponse.ok()::bodyValue)
            .onErrorResume(ExchangeException::class.java) {
                ServerResponse
                    .badRequest()
                    .bodyValue(ErrorMessage(it.message))
            }
}