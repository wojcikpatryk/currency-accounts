package com.wojcik.patryk.currencyaccounts.core.exception.validation

import com.wojcik.patryk.currencyaccounts.web.rest.dto.account.AccountRegisterDTO

class AccountRegisterValidationException(
    account: AccountRegisterDTO
) : Exception("Invalid account: ${account.personalIdNumber}")