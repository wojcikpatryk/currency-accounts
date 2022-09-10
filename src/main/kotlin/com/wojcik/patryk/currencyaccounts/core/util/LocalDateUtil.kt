package com.wojcik.patryk.currencyaccounts.core.util

import java.time.LocalDate
import java.time.Period

private const val ADULT_AGE = 18

fun LocalDate.isAdult() =
    Period
        .between(this, LocalDate.now())
        .years >= ADULT_AGE