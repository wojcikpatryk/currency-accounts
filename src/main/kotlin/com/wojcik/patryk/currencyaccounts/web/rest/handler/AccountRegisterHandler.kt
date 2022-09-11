package com.wojcik.patryk.currencyaccounts.web.rest.handler

import com.wojcik.patryk.currencyaccounts.domain.account.service.AccountRegisterService
import com.wojcik.patryk.currencyaccounts.web.rest.dto.account.AccountRegistration
import com.wojcik.patryk.currencyaccounts.web.rest.dto.error.ErrorMessage
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class AccountRegisterHandler(
    private val registerService: AccountRegisterService
) {

    fun register(request: ServerRequest) =
        request
            .bodyToMono(AccountRegistration::class.java)
            .flatMap(registerService::register)
            .flatMap(ServerResponse.ok()::bodyValue)
            .onErrorResume { ServerResponse.badRequest().bodyValue(ErrorMessage(it.message)) }
}