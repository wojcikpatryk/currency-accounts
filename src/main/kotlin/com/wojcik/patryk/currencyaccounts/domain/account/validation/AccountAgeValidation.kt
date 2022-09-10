package com.wojcik.patryk.currencyaccounts.domain.account.validation

import com.wojcik.patryk.currencyaccounts.core.util.isAdult
import com.wojcik.patryk.currencyaccounts.web.rest.dto.account.AccountRegisterDTO
import org.springframework.stereotype.Component
import pl.utkala.polishidentifiersutils.PeselUtil
import java.time.LocalDate

@Component
class AccountAgeValidation : AccountValidation {

    override fun invoke(account: AccountRegisterDTO) =
        PeselUtil(account.personalIdNumber)
            .getBirthDate()
            ?.let(LocalDate::parse)
            ?.let(LocalDate::isAdult)
            ?: false
}