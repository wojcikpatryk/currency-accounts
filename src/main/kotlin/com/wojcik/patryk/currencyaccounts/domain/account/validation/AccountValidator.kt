package com.wojcik.patryk.currencyaccounts.domain.account.validation

import com.wojcik.patryk.currencyaccounts.core.exception.validation.AccountRegisterValidationException
import com.wojcik.patryk.currencyaccounts.core.util.flatMapIfTrue
import com.wojcik.patryk.currencyaccounts.web.rest.dto.account.AccountRegistration
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono

@Component
class AccountValidator(
    private val validations: List<AccountValidation>
) {

    fun validate(account: AccountRegistration) =
        validations
            .toFlux()
            .all { check -> check(account) }
            .flatMapIfTrue(account::toMono)
            .switchIfEmpty { Mono.error { AccountRegisterValidationException(account) } }
}