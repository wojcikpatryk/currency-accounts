package com.wojcik.patryk.currencyaccounts.web.rest.handler

import com.wojcik.patryk.currencyaccounts.AbstractTest
import com.wojcik.patryk.currencyaccounts.core.enums.AccountCurrency
import com.wojcik.patryk.currencyaccounts.domain.account.model.Account
import com.wojcik.patryk.currencyaccounts.domain.account.repository.AccountRepository
import com.wojcik.patryk.currencyaccounts.domain.subaccount.model.SubAccount
import com.wojcik.patryk.currencyaccounts.domain.subaccount.repository.SubAccountRepository
import okhttp3.mockwebserver.MockWebServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.mock.web.reactive.function.server.MockServerRequest
import reactor.test.StepVerifier

class AccountDownloadHandlerTest extends AbstractTest {

    @Autowired
    private AccountDownloadHandler handler

    @Autowired
    private AccountRepository accountRepository

    @Autowired
    private SubAccountRepository subAccountRepository

    @Autowired
    private MockWebServer server

    def 'should get persisted account'() {
        given:
        def personalId = "88030246447"
        accountRepository.save(new Account(null, "Test", "Test", personalId)).subscribe()
        subAccountRepository.save(new SubAccount(null, 1L, AccountCurrency.PLN, BigDecimal.TEN)).subscribe()
        def request = MockServerRequest.builder().pathVariable("personalId", personalId).build()

        when:
        def result = handler.getAccount(request)

        then:
        StepVerifier.create(result)
                .expectNextMatches { it.statusCode() == HttpStatus.OK }
                .verifyComplete()
    }

    def 'should not found account by not persisted personalId'() {
        given:
        def personalId = "88030246447"
        accountRepository.save(new Account(null, "Test", "Test", personalId)).subscribe()
        subAccountRepository.save(new SubAccount(null, 1L, AccountCurrency.PLN, BigDecimal.TEN)).subscribe()
        def request = MockServerRequest.builder().pathVariable("personalId", "88030246446").build()

        when:
        def result = handler.getAccount(request)

        then:
        StepVerifier.create(result)
                .expectNextMatches { it.statusCode() == HttpStatus.NOT_FOUND }
                .verifyComplete()
    }
}
