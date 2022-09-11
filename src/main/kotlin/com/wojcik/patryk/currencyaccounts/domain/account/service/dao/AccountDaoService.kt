package com.wojcik.patryk.currencyaccounts.domain.account.service.dao

import com.wojcik.patryk.currencyaccounts.core.enums.AccountCurrency
import com.wojcik.patryk.currencyaccounts.core.exception.register.UniqueAccountRegisterException
import com.wojcik.patryk.currencyaccounts.domain.account.model.Account
import com.wojcik.patryk.currencyaccounts.domain.account.model.SavedAccount
import com.wojcik.patryk.currencyaccounts.domain.account.repository.AccountRepository
import com.wojcik.patryk.currencyaccounts.domain.subaccount.model.SubAccount
import com.wojcik.patryk.currencyaccounts.domain.subaccount.repository.SubAccountRepository
import com.wojcik.patryk.currencyaccounts.web.rest.dto.account.AccountRegisterDTO
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

    fun save(account: AccountRegisterDTO) =
        accountRepository
            .save(Account.of(account))
            .onErrorMap(DataIntegrityViolationException::class.java) { UniqueAccountRegisterException(account.personalIdNumber) }
            .doOnSuccess { log.info("Account saved: ${it.personalIdNumber}") }
            .doOnError { log.error("Account not saved: ${it.message}", it) }
            .flatMap { createAccount(it, account.startingBalanceInPLN) }

    fun findByPersonalIdNumber(personalIdNumber: String) =
        accountRepository
            .findByPersonalIdNumber(personalIdNumber)
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
    ) = setOf(
        SubAccount(accountId = account.id, currency = AccountCurrency.PLN, balance = startingBalanceInPLN),
        SubAccount(accountId = account.id, currency = AccountCurrency.USD, balance = BigDecimal.ZERO)
    )

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