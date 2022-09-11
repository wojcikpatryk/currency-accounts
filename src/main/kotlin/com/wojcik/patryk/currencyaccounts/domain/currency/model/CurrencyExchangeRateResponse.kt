package com.wojcik.patryk.currencyaccounts.domain.currency.model

import java.time.LocalDate

data class CurrencyExchangeRateResponse(
    val effectiveDate: LocalDate,
    val rates: List<CurrencyRateResponse>
)