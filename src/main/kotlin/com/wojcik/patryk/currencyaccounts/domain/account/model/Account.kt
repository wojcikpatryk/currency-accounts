package com.wojcik.patryk.currencyaccounts.domain.account.model

import com.wojcik.patryk.currencyaccounts.web.rest.dto.account.AccountRegisterDTO
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("accounts")
data class Account(
    @Id
    val id: Long? = null,
    val firstName: String,
    val lastName: String,
    val personalIdNumber: String
) {

    companion object {
        fun of(account: AccountRegisterDTO) =
            Account(
                firstName = account.firstName,
                lastName = account.lastName,
                personalIdNumber = account.personalIdNumber
            )
    }
}