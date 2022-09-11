package com.wojcik.patryk.currencyaccounts.core.config.webclient

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfiguration(
    @Value("\${nbp.api.server.url}") val nbpApiUrl: String
) {

    @Bean
    fun webClient() =
        WebClient
            .builder()
            .baseUrl(nbpApiUrl)
            .build()
}