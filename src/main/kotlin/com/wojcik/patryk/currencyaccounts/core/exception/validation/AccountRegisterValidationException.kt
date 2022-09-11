package com.wojcik.patryk.currencyaccounts.core.exception.validation

import com.wojcik.patryk.currencyaccounts.web.rest.dto.account.AccountRegistration

class AccountRegisterValidationException(
    account: AccountRegistration
) : Exception("Invalid account: ${account.personalId}")