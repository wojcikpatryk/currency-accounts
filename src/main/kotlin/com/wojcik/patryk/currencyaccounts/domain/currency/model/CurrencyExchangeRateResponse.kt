package com.wojcik.patryk.currencyaccounts.domain.currency.model

import com.wojcik.patryk.currencyaccounts.core.enums.AccountCurrency

data class CurrencyExchangeRateResponse(
    val code: AccountCurrency,
    val rates: List<CurrencyRateResponse>
)