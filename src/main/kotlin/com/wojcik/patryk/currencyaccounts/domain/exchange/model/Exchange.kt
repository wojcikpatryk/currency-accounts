package com.wojcik.patryk.currencyaccounts.domain.exchange.model

import com.wojcik.patryk.currencyaccounts.core.enums.AccountCurrency
import java.math.BigDecimal

data class Exchange(
    val personalId: String,
    val from: AccountCurrency,
    val to: AccountCurrency,
    val amount: BigDecimal
)