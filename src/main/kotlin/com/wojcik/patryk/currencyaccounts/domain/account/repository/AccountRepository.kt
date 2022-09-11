package com.wojcik.patryk.currencyaccounts.domain.account.repository

import com.wojcik.patryk.currencyaccounts.domain.account.model.Account
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface AccountRepository : R2dbcRepository<Account, Long> {

    fun findByPersonalIdNumber(personalIdNumber: String): Mono<Account>
}