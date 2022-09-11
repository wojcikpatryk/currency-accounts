package com.wojcik.patryk.currencyaccounts.core.util

import com.wojcik.patryk.currencyaccounts.core.enums.AccountCurrency
import java.util.*

val DEFAULT_CURRENCY: Currency = Currency.getInstance(Locale.getDefault())
val DEFAULT_ACCOUNT_CURRENCY = AccountCurrency.valueOf(DEFAULT_CURRENCY.currencyCode)