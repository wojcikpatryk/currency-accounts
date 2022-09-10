package com.wojcik.patryk.currencyaccounts.domain.account.validation

import com.wojcik.patryk.currencyaccounts.web.rest.dto.account.AccountRegisterDTO
import org.springframework.stereotype.Component
import pl.utkala.polishidentifiersutils.PeselUtil

@Component
class AccountPersonalIdNumberValidation : AccountValidation {

    override fun invoke(account: AccountRegisterDTO) =
        PeselUtil(account.personalIdNumber).isValid()
}