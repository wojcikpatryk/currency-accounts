package com.wojcik.patryk.currencyaccounts.domain.currency.model

import com.wojcik.patryk.currencyaccounts.core.enums.AccountCurrency
import java.math.BigDecimal
import java.time.LocalDate

data class CurrencyExchangeRate(
    val code: AccountCurrency,
    val date: LocalDate,
    val value: BigDecimal
)