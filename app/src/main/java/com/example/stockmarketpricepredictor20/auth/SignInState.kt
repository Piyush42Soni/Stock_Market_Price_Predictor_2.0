package com.example.stockmarketpricepredictor20.auth

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)