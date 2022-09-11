package com.wojcik.patryk.currencyaccounts.domain.account.model

import com.wojcik.patryk.currencyaccounts.core.enums.AccountCurrency
import com.wojcik.patryk.currencyaccounts.core.exception.subaccount.SubAccountNotFoundException
import com.wojcik.patryk.currencyaccounts.domain.subaccount.model.SubAccount

data class SavedAccount(
    val account: Account,
    val subAccounts: List<SubAccount>
) {

    fun getSubAccount(currency: AccountCurrency) =
        subAccounts
            .find { it.currency == currency }
            ?: throw SubAccountNotFoundException(account.personalId, currency)
}