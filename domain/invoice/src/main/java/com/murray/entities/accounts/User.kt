package com.murray.entities.accounts

/**
 * Al utilizar data class se implementa de forma automática el método equals, toString, copy y hashCode,
 * teniendo en cuenta las propiedades declaradas en el constructuro primario/principal
 */
open class User(open var name: String, open var surname: String, open var email: String) : Comparable<User> {
    override fun compareTo(other: User): Int {
        return name.compareTo(other.name)
    }

    override fun equals(other: Any?): Boolean {
        return email.equals((other as User).email)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return "$name $surname $email"
    }
}
