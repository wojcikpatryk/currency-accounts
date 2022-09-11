package com.wojcik.patryk.currencyaccounts.domain.currency.cache.key

import com.wojcik.patryk.currencyaccounts.core.enums.AccountCurrency
import java.time.LocalDate

data class CurrencyExchangeRateCacheKey(
    val date: LocalDate,
    val currency: AccountCurrency
)