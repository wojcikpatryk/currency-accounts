package com.wojcik.patryk.currencyaccounts.web.rest.handler

import com.wojcik.patryk.currencyaccounts.AbstractTest
import com.wojcik.patryk.currencyaccounts.web.rest.dto.account.AccountRegistration
import okhttp3.mockwebserver.MockWebServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.mock.web.reactive.function.server.MockServerRequest
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Unroll

class AccountRegisterHandlerTest extends AbstractTest {

    @Autowired
    private AccountRegisterHandler handler

    @Autowired
    private MockWebServer server

    @Unroll("Account #personalId registration with amount #amount should return #status")
    def 'registration test'() {
        given:
        def account = new AccountRegistration("First", "Last", amount, personalId)
        def request = MockServerRequest.builder().body(Mono.just(account))

        when:
        def result = handler.register(request)

        then:
        StepVerifier.create(result)
                .expectNextMatches { it.statusCode() == status }
                .verifyComplete()

        where:
        amount          | personalId    | status
        BigDecimal.TEN  | "88030246447" | HttpStatus.OK
        BigDecimal.TEN  | "88030246555" | HttpStatus.BAD_REQUEST
        BigDecimal.TEN  | "88030246447" | HttpStatus.BAD_REQUEST
        BigDecimal.TEN  | "08280345886" | HttpStatus.BAD_REQUEST
        BigDecimal.ZERO | "91080393267" | HttpStatus.OK
    }
}
