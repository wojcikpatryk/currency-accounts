package com.wojcik.patryk.currencyaccounts.domain.account.validation

import com.wojcik.patryk.currencyaccounts.web.rest.dto.account.AccountRegisterDTO

interface AccountValidation : (AccountRegisterDTO) -> Boolean