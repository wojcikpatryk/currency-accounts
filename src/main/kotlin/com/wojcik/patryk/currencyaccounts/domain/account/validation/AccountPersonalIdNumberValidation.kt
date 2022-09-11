package com.wojcik.patryk.currencyaccounts.domain.account.validation

import com.wojcik.patryk.currencyaccounts.web.rest.dto.account.AccountRegistration
import org.springframework.stereotype.Component
import pl.utkala.polishidentifiersutils.PeselUtil

@Component
class AccountPersonalIdValidation : AccountValidation {

    override fun invoke(account: AccountRegistration) =
        PeselUtil(account.personalId).isValid()
}