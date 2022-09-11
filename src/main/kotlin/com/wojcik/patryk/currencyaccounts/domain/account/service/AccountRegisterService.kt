package com.wojcik.patryk.currencyaccounts.domain.account.service

import com.wojcik.patryk.currencyaccounts.domain.account.service.dao.AccountDaoService
import com.wojcik.patryk.currencyaccounts.domain.account.validation.AccountValidator
import com.wojcik.patryk.currencyaccounts.web.rest.dto.account.AccountRegisterDTO
import org.springframework.stereotype.Service

@Service
class AccountRegisterService(
    private val validator: AccountValidator,
    private val dao: AccountDaoService
) {

    fun register(account: AccountRegisterDTO) =
        validator
            .validate(account)
            .flatMap(dao::save)
}