package com.wojcik.patryk.currencyaccounts.web.rest.dto

import java.math.BigDecimal

data class AccountRegisterDTO(
    val firstName: String,
    val lastName: String,
    val personalIdNumber: String,
    val startingBalanceInPLN: BigDecimal
)