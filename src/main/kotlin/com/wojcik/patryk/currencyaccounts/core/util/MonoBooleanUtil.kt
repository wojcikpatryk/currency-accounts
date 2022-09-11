package com.wojcik.patryk.currencyaccounts.core.util

import reactor.core.publisher.Mono

fun <T> Mono<Boolean>.flatMapIfTrue(supplier: () -> Mono<T>) =
    this.flatMap { if (it) supplier() else Mono.empty() }