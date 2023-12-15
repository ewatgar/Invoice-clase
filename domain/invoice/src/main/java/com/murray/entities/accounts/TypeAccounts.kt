package com.murray.entities.accounts

enum class TypeAccounts(val value:String) {
    PRIVATE("Privado"),
    PUBLIC("Publico"),
    EMPTY("Vacio");

    companion object {
        fun getTypeAccountsList(): ArrayList<TypeAccounts> {
            return arrayListOf(PRIVATE, PUBLIC, EMPTY)
        }
    }

    override fun toString(): String {
        return value
    }
}