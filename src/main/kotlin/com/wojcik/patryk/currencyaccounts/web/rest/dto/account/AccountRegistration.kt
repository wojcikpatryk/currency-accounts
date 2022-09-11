package com.wojcik.patryk.currencyaccounts.web.rest.dto.account

import java.math.BigDecimal

data class AccountRegistration(
    val firstName: String,
    val lastName: String,
    val startingBalanceInPLN: BigDecimal,
    val personalId: String
)