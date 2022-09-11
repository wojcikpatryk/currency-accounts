package com.wojcik.patryk.currencyaccounts.domain.account.validation

import com.wojcik.patryk.currencyaccounts.core.util.isAdult
import com.wojcik.patryk.currencyaccounts.web.rest.dto.account.AccountRegistration
import org.springframework.stereotype.Component
import pl.utkala.polishidentifiersutils.PeselUtil
import java.time.LocalDate

@Component
class AccountAgeValidation : AccountValidation {

    override fun invoke(account: AccountRegistration) =
        PeselUtil(account.personalId)
            .getBirthDate()
            ?.let(LocalDate::parse)
            ?.let(LocalDate::isAdult)
            ?: false
}