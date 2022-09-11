package com.wojcik.patryk.currencyaccounts.domain.currency.model

import java.math.BigDecimal
import java.time.LocalDate

data class CurrencyRateResponse(
    val effectiveDate: LocalDate,
    val mid: BigDecimal
)