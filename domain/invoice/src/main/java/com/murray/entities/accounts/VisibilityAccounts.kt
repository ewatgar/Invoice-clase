package com.murray.entities.accounts

enum class VisibilityAccounts(val value: String) {
    USER("Usuario"),
    ADMIN("Administrador"),
    GUEST("Invitado"),
    CUSTOMER("Cliente");

    companion object {
        fun getVisibilityAccountsList(): ArrayList<VisibilityAccounts> {
            return arrayListOf(USER, ADMIN, GUEST, CUSTOMER)
        }

    }

    override fun toString(): String {
        return value
    }
}