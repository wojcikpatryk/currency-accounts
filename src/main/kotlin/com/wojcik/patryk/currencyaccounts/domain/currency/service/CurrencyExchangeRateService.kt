package com.wojcik.patryk.currencyaccounts.domain.currency.service

import com.wojcik.patryk.currencyaccounts.core.api.currency.CurrencyExchangeRateAPI
import com.wojcik.patryk.currencyaccounts.core.enums.AccountCurrency
import com.wojcik.patryk.currencyaccounts.core.util.DEFAULT_ACCOUNT_CURRENCY
import com.wojcik.patryk.currencyaccounts.domain.currency.cache.CurrencyExchangeRateCache
import com.wojcik.patryk.currencyaccounts.domain.currency.model.CurrencyExchangeRate
import org.springframework.stereotype.Service
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.math.BigDecimal
import java.time.LocalDate

@Service
class CurrencyExchangeRateService(
    private val currencyExchangeRateAPI: CurrencyExchangeRateAPI,
    private val currencyExchangeRateCache: CurrencyExchangeRateCache
) {

    fun getCurrencyExchangeRate(currency: AccountCurrency) =
        currencyExchangeRateCache
            .findNewest(currency)

    fun refreshCache() =
        AccountCurrency.values()
            .filter { it != DEFAULT_ACCOUNT_CURRENCY }
            .toFlux()
            .flatMap(currencyExchangeRateAPI::getCurrencyExchangeRates)
            .mergeWith(CurrencyExchangeRate(DEFAULT_ACCOUNT_CURRENCY, LocalDate.now(), BigDecimal.ONE).toMono())
            .map(currencyExchangeRateCache::put)
}