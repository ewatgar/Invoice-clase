package com.murray.entities.accounts

data class UserSignUp(var password:String, var typeAccount:TypeAccounts,var visibilityAccount: VisibilityAccounts, override var name: String, override var surname: String, override var email: String):User(name,surname, email)
