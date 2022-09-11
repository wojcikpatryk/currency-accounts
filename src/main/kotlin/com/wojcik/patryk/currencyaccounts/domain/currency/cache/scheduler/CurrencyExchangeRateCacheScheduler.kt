package com.wojcik.patryk.currencyaccounts.domain.currency.cache.scheduler

import com.wojcik.patryk.currencyaccounts.domain.currency.service.CurrencyExchangeRateService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class CurrencyExchangeRateCacheScheduler(
    private val exchangeRateService: CurrencyExchangeRateService
) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    init {
        refresh()
    }

    @Scheduled(cron = "0 12 30 * * ?")
    fun schedule() {
        log.info("Downloading currency exchange rates...")
        refresh()
    }

    private fun refresh() {
        exchangeRateService
            .refreshCache()
            .subscribe { log.info("Currency exchange rates downloaded: $it") }
    }
}