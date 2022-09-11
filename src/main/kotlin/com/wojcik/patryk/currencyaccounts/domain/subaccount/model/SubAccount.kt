package com.wojcik.patryk.currencyaccounts.domain.subaccount.model

import com.wojcik.patryk.currencyaccounts.core.enums.AccountCurrency
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("subaccounts")
data class SubAccount(
    @Id
    val id: Long? = null,
    val accountId: Long? = null,
    val currency: AccountCurrency,
    val balance: BigDecimal
)