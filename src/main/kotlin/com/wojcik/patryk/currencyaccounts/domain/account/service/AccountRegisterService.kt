package com.wojcik.patryk.currencyaccounts.domain.account.service

import com.wojcik.patryk.currencyaccounts.domain.account.service.dao.AccountDaoService
import com.wojcik.patryk.currencyaccounts.domain.account.validation.AccountValidator
import com.wojcik.patryk.currencyaccounts.web.rest.dto.account.AccountRegistration
import org.springframework.stereotype.Service

@Service
class AccountRegisterService(
    private val accountValidator: AccountValidator,
    private val accountDaoService: AccountDaoService
) {

    fun register(account: AccountRegistration) =
        accountValidator
            .validate(account)
            .flatMap(accountDaoService::save)
}