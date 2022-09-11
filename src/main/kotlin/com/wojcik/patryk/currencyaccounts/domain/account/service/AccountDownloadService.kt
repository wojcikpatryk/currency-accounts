package com.wojcik.patryk.currencyaccounts.domain.account.service

import com.wojcik.patryk.currencyaccounts.core.enums.AccountCurrency
import com.wojcik.patryk.currencyaccounts.domain.account.model.SavedAccount
import com.wojcik.patryk.currencyaccounts.domain.account.service.dao.AccountDaoService
import com.wojcik.patryk.currencyaccounts.domain.currency.service.CurrencyExchangeRateService
import com.wojcik.patryk.currencyaccounts.domain.subaccount.model.SubAccount
import org.springframework.stereotype.Service
import reactor.kotlin.core.publisher.toMono
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

@Service
class AccountDownloadService(
    private val daoService: AccountDaoService,
    private val exchangeRateService: CurrencyExchangeRateService
) {

    fun getAccount(personalIdNumber: String) =
        daoService
            .findByPersonalIdNumber(personalIdNumber)
            .flatMap { updateCurrencies(it) }

    private fun updateCurrencies(account: SavedAccount) = account
        .subAccounts
        .map { it.copy(balance = exchange(it.balance, it.currency)) }
        .let { account.copy(subAccounts = it) }
        .let(daoService::update)

    private fun exchange(
        balanceInPLN: BigDecimal,
        currency: AccountCurrency
    ) = when (currency) {
        AccountCurrency.PLN -> balanceInPLN
        else -> balanceInPLN
            .divide(exchangeRateService.getCurrencyExchangeRate(currency).value)
            .setScale(2, RoundingMode.HALF_EVEN)
    }

    fun refresh() = exchangeRateService.refreshCache()

}