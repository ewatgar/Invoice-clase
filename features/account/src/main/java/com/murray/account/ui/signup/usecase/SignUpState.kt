package com.murray.account.ui.signup.usecase

import com.murray.entities.accounts.UserSignUp

sealed class SignUpState {
    data object EmailEmptyError : SignUpState()
    data object PasswordEmptyError: SignUpState()
    data object PasswordEmptyError2: SignUpState()
    data object PasswordsNotEquals: SignUpState()
    data object EmailDuplicatedError: SignUpState()

    //data object Completed: SignUpState()
    data class Success(var userSignUp: UserSignUp?): SignUpState()

    data class Loading(var value: Boolean) : SignUpState()
}