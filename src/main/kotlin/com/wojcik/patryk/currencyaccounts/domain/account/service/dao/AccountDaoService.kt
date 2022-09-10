package com.wojcik.patryk.currencyaccounts.domain.account.service.dao

import com.wojcik.patryk.currencyaccounts.core.exception.register.UniqueAccountRegisterException
import com.wojcik.patryk.currencyaccounts.domain.account.model.Account
import com.wojcik.patryk.currencyaccounts.domain.account.repository.AccountRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class AccountDaoService(
    private val repository: AccountRepository
) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    fun save(account: Account) =
        repository
            .save(account)
            .onErrorMap(DataIntegrityViolationException::class.java) { UniqueAccountRegisterException(account.personalIdNumber) }
            .doOnSuccess { log.info("Account saved: ${it.personalIdNumber}") }
            .doOnError { log.error("Account not saved: ${it.message}", it) }
}