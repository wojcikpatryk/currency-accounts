package com.wojcik.patryk.currencyaccounts.domain.account.service

import com.wojcik.patryk.currencyaccounts.domain.account.service.dao.AccountDaoService
import org.springframework.stereotype.Service

@Service
class AccountDownloadService(
    private val accountDaoService: AccountDaoService
) {

    fun getAccount(personalId: String) =
        accountDaoService
            .findByPersonalId(personalId)
}