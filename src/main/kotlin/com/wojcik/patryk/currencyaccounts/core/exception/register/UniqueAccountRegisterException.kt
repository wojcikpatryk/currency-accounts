package com.wojcik.patryk.currencyaccounts.core.exception.register

class UniqueAccountRegisterException(
    personalIdNumber: String
) : Exception("User $personalIdNumber already exists")