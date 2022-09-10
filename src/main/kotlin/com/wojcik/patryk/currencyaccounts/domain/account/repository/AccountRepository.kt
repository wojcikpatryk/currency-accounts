package com.wojcik.patryk.currencyaccounts.domain.account.repository

import com.wojcik.patryk.currencyaccounts.domain.account.model.Account
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : ReactiveCrudRepository<Account, Long>