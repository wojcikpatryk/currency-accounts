package com.wojcik.patryk.currencyaccounts.web.rest.handler

import com.wojcik.patryk.currencyaccounts.domain.account.service.AccountRegisterService
import com.wojcik.patryk.currencyaccounts.web.rest.dto.account.AccountRegisterDTO
import com.wojcik.patryk.currencyaccounts.web.rest.dto.error.ErrorDTO
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class AccountRegisterHandler(
    private val registerService: AccountRegisterService
) {

    fun register(request: ServerRequest) =
        request
            .bodyToMono(AccountRegisterDTO::class.java)
            .flatMap(registerService::register)
            .flatMap(ServerResponse.ok()::bodyValue)
            .onErrorResume { ServerResponse.badRequest().bodyValue(ErrorDTO(it.message)) }
}