package com.wojcik.patryk.currencyaccounts.domain.account.service.dao

import com.wojcik.patryk.currencyaccounts.core.enums.AccountCurrency
import com.wojcik.patryk.currencyaccounts.core.exception.register.UniqueAccountRegisterException
import com.wojcik.patryk.currencyaccounts.core.util.DEFAULT_ACCOUNT_CURRENCY
import com.wojcik.patryk.currencyaccounts.domain.account.model.Account
import com.wojcik.patryk.currencyaccounts.domain.account.model.SavedAccount
import com.wojcik.patryk.currencyaccounts.domain.account.repository.AccountRepository
import com.wojcik.patryk.currencyaccounts.domain.subaccount.model.SubAccount
import com.wojcik.patryk.currencyaccounts.domain.subaccount.repository.SubAccountRepository
import com.wojcik.patryk.currencyaccounts.web.rest.dto.account.AccountRegistration
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import reactor.kotlin.core.publisher.toMono
import java.math.BigDecimal

@Service
class AccountDaoService(
    private val accountRepository: AccountRepository,
    private val subAccountRepository: SubAccountRepository
) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    fun save(account: AccountRegistration) =
        accountRepository
            .save(Account.of(account))
            .onErrorMap(DataIntegrityViolationException::class.java) { UniqueAccountRegisterException(account.personalId) }
            .doOnSuccess { log.info("Account saved: ${it.personalId}") }
            .doOnError { log.error("Account not saved: ${it.message}", it) }
            .flatMap { createAccount(it, account.startingBalanceInPLN) }

    fun findByPersonalId(personalId: String) =
        accountRepository
            .findByPersonalId(personalId)
            .flatMap(::getSubAccounts)

    private fun createAccount(
        account: Account,
        startingBalanceInPLN: BigDecimal
    ) = subAccountRepository
        .saveAll(subAccounts(account, startingBalanceInPLN))
        .collectList()
        .map { SavedAccount(account, it) }

    private fun subAccounts(
        account: Account,
        startingBalanceInPLN: BigDecimal
    ) = AccountCurrency
        .values()
        .filter { it != DEFAULT_ACCOUNT_CURRENCY }
        .map { SubAccount(accountId = account.id, currency = it, balance = BigDecimal.ZERO) }
        .plus(SubAccount(accountId = account.id, currency = DEFAULT_ACCOUNT_CURRENCY, balance = startingBalanceInPLN))

    private fun getSubAccounts(account: Account) =
        account
            .id
            .toMono()
            .flatMapMany(subAccountRepository::findAllByAccountId)
            .collectList()
            .map { SavedAccount(account, it) }

    fun update(account: SavedAccount) =
        subAccountRepository
            .saveAll(account.subAccounts)
            .collectList()
            .map { account.copy(subAccounts = it) }
}