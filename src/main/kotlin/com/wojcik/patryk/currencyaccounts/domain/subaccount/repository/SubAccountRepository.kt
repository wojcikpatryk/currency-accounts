package com.wojcik.patryk.currencyaccounts.domain.subaccount.repository

import com.wojcik.patryk.currencyaccounts.domain.subaccount.model.SubAccount
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface SubAccountRepository : R2dbcRepository<SubAccount, Long> {

    fun findAllByAccountId(accountId: Long): Flux<SubAccount>
}