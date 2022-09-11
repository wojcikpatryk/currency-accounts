package com.wojcik.patryk.currencyaccounts.domain.currency.cache

import com.wojcik.patryk.currencyaccounts.core.enums.AccountCurrency
import com.wojcik.patryk.currencyaccounts.domain.currency.cache.key.CurrencyExchangeRateCacheKey
import com.wojcik.patryk.currencyaccounts.domain.currency.model.CurrencyExchangeRate
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class CurrencyExchangeRateCache {

    private val cache = ConcurrentHashMap<CurrencyExchangeRateCacheKey, CurrencyExchangeRate>()

    fun put(exchangeRate: CurrencyExchangeRate): CurrencyExchangeRate =
        cache.getOrPut(CurrencyExchangeRateCacheKey(exchangeRate.date, exchangeRate.code)) {
            exchangeRate
        }

    fun findNewest(currency: AccountCurrency) =
        find { this.currency == currency }
            .minByOrNull { it.date }!!

    private fun find(predicate: CurrencyExchangeRateCacheKey.() -> Boolean) =
        cache
            .entries
            .filter { predicate(it.key) }
            .map { it.value }
}