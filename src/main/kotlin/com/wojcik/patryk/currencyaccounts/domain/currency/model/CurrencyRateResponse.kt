package com.wojcik.patryk.currencyaccounts.domain.currency.model

import java.math.BigDecimal

data class CurrencyRateResponse(
    val code: String,
    val mid: BigDecimal
)