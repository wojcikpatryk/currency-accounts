package com.wojcik.patryk.currencyaccounts.core.exception.register

class UniqueAccountRegisterException(
    personalId: String
) : Exception("User $personalId already exists")