package com.wojcik.patryk.currencyaccounts.domain.exchange.service

import com.wojcik.patryk.currencyaccounts.core.exception.exchange.ExchangeException
import com.wojcik.patryk.currencyaccounts.domain.account.model.SavedAccount
import com.wojcik.patryk.currencyaccounts.domain.account.service.dao.AccountDaoService
import com.wojcik.patryk.currencyaccounts.domain.currency.service.CurrencyExchangeRateService
import com.wojcik.patryk.currencyaccounts.domain.exchange.model.Exchange
import com.wojcik.patryk.currencyaccounts.domain.subaccount.repository.SubAccountRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.toMono
import java.math.BigDecimal
import java.math.RoundingMode

@Service
class CurrencyExchangeService(
    private val accountDaoService: AccountDaoService,
    private val exchangeRateService: CurrencyExchangeRateService,
    private val subAccountRepository: SubAccountRepository
) {

    fun exchange(exchange: Exchange) =
        when (exchange.amount > BigDecimal.ZERO) {
            false -> Mono.error { ExchangeException(exchange) }
            else -> exchangeMoney(exchange)
        }

    private fun exchangeMoney(exchange: Exchange) = accountDaoService
        .findByPersonalId(exchange.personalId)
        .flatMapMany { exchange(exchange, it) }
        .flatMap(subAccountRepository::save)
        .then(accountDaoService.findByPersonalId(exchange.personalId))

    private fun exchange(
        exchange: Exchange,
        account: SavedAccount
    ) = deductMoney(exchange, account)
        .mergeWith(addMoney(exchange, account))

    private fun deductMoney(exchange: Exchange, account: SavedAccount) =
        account
            .getSubAccount(exchange.from)
            .takeIf { it.balance >= exchange.amount }
            ?.let { it.copy(balance = it.balance - exchange.amount) }
            .toMono()
            .switchIfEmpty { Mono.error { ExchangeException(exchange) } }

    private fun addMoney(exchange: Exchange, account: SavedAccount) =
        account
            .getSubAccount(exchange.to)
            .let {
                it to exchange
                    .amount
                    .multiply(exchangeRateService.getCurrencyExchangeRate(exchange.from).value)
                    .divide(exchangeRateService.getCurrencyExchangeRate(exchange.to).value, 2, RoundingMode.HALF_EVEN)
            }
            .let { (subAccount, amount) -> subAccount.copy(balance = subAccount.balance + amount) }
            .toMono()
}