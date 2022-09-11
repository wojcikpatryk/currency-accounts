package com.wojcik.patryk.currencyaccounts.core.exception.subaccount

import com.wojcik.patryk.currencyaccounts.core.enums.AccountCurrency

class SubAccountNotFoundException(
    personalId: String,
    currency: AccountCurrency
) : Exception("SubAccount $currency not found for $personalId")