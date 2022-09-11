package com.wojcik.patryk.currencyaccounts.core.exception.exchange

import com.wojcik.patryk.currencyaccounts.domain.exchange.model.Exchange

class ExchangeException(exchange: Exchange) : Exception("Exchange between ${exchange.from} - ${exchange.to} failed") {
}