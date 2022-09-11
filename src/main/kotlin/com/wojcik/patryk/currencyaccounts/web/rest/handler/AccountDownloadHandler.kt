package com.wojcik.patryk.currencyaccounts.web.rest.handler

import com.wojcik.patryk.currencyaccounts.domain.account.service.AccountDownloadService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.kotlin.core.publisher.switchIfEmpty

@Component
class AccountDownloadHandler(
    private val downloadService: AccountDownloadService
) {

    fun getAccount(request: ServerRequest) =
        request
            .pathVariable("personalId")
            .let(downloadService::getAccount)
            .flatMap(ServerResponse.ok()::bodyValue)
            .switchIfEmpty(ServerResponse.notFound()::build)
}