package com.wojcik.patryk.currencyaccounts.core.enums

enum class AccountCurrency {

    PLN,
    USD;

    companion object {
        fun exists(currency: String) =
            values()
                .any { it.name == currency }
    }
}