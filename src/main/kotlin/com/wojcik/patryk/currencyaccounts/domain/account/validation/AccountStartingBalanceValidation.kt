package com.wojcik.patryk.currencyaccounts.domain.account.validation

import com.wojcik.patryk.currencyaccounts.web.rest.dto.account.AccountRegisterDTO
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class AccountStartingBalanceValidation : AccountValidation {

    override fun invoke(account: AccountRegisterDTO) =
        account.startingBalanceInPLN >= BigDecimal.ZERO
}