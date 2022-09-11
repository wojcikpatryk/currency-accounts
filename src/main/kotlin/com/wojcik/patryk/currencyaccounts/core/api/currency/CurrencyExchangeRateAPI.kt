package com.wojcik.patryk.currencyaccounts.core.api.currency

import com.wojcik.patryk.currencyaccounts.core.enums.AccountCurrency
import com.wojcik.patryk.currencyaccounts.domain.currency.model.CurrencyExchangeRate
import com.wojcik.patryk.currencyaccounts.domain.currency.model.CurrencyExchangeRateResponse
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class CurrencyExchangeRateAPI(
    private val client: WebClient
) {

    companion object {
        private const val TABLE = "A"
        private const val EXCHANGE_RATES_URL = "/exchangerates/rates/$TABLE"
    }

    fun getCurrencyExchangeRate(currency: AccountCurrency) =
        client
            .get()
            .uri("${EXCHANGE_RATES_URL}/$currency")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(CurrencyExchangeRateResponse::class.java)
            .map(::toCurrencyExchangeRate)

    private fun toCurrencyExchangeRate(response: CurrencyExchangeRateResponse) =
        response
            .rates
            .minByOrNull { it.effectiveDate }!!
            .let { CurrencyExchangeRate(response.code, it.effectiveDate, it.mid) }
}