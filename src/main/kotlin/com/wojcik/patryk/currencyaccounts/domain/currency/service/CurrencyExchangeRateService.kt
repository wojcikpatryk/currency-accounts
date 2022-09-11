package com.wojcik.patryk.currencyaccounts.domain.currency.service

import com.wojcik.patryk.currencyaccounts.core.api.currency.CurrencyExchangeRateAPI
import com.wojcik.patryk.currencyaccounts.core.enums.AccountCurrency
import com.wojcik.patryk.currencyaccounts.domain.currency.cache.CurrencyExchangeRateCache
import org.springframework.stereotype.Service
import reactor.kotlin.core.publisher.toFlux

@Service
class CurrencyExchangeRateService(
    private val api: CurrencyExchangeRateAPI,
    private val cache: CurrencyExchangeRateCache
) {

    fun getCurrencyExchangeRate(currency: AccountCurrency = AccountCurrency.USD) =
        cache
            .findNewest(currency)

    fun refreshCache() =
        AccountCurrency.values()
            .filter { it != AccountCurrency.PLN }
            .toFlux()
            .flatMap(api::getCurrencyExchangeRate)
            .map(cache::put)
}