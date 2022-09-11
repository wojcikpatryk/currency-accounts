package com.wojcik.patryk.currencyaccounts.domain.currency.cache.scheduler

import com.wojcik.patryk.currencyaccounts.domain.currency.service.CurrencyExchangeRateService
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@Profile("!test")
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
        refresh()
    }

    private fun refresh() {
        log.info("Downloading currency exchange rates...")
        exchangeRateService
            .refreshCache()
            .subscribe { log.info("Currency exchange rates downloaded: $it") }
    }
}