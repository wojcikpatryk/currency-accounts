package com.wojcik.patryk.currencyaccounts.core.api.currency

import com.wojcik.patryk.currencyaccounts.core.enums.AccountCurrency
import com.wojcik.patryk.currencyaccounts.domain.currency.model.CurrencyExchangeRate
import com.wojcik.patryk.currencyaccounts.domain.currency.model.CurrencyExchangeRateResponse
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.kotlin.core.publisher.toFlux

@Component
class CurrencyExchangeRateAPI(
    private val client: WebClient
) {

    companion object {
        private const val TABLE = "A"
        private const val EXCHANGE_RATES_URL = "/exchangerates/tables/$TABLE"
    }

    fun getCurrencyExchangeRates(currency: AccountCurrency) =
        client
            .get()
            .uri(EXCHANGE_RATES_URL)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(CurrencyExchangeRateResponse::class.java)
            .flatMap(::toCurrencyExchangeRate)

    private fun toCurrencyExchangeRate(response: CurrencyExchangeRateResponse) =
        response
            .rates
            .toFlux()
            .filter { AccountCurrency.exists(it.code) }
            .map { CurrencyExchangeRate(AccountCurrency.valueOf(it.code), response.effectiveDate, it.mid) }
}