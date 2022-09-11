package com.wojcik.patryk.currencyaccounts.domain.account.model

import com.wojcik.patryk.currencyaccounts.web.rest.dto.account.AccountRegistration
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("accounts")
data class Account(
    @Id
    val id: Long? = null,
    val firstName: String,
    val lastName: String,
    val personalId: String
) {

    companion object {
        fun of(account: AccountRegistration) =
            Account(
                firstName = account.firstName,
                lastName = account.lastName,
                personalId = account.personalId
            )
    }
}