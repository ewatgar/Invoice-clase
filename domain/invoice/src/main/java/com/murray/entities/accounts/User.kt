package com.murray.entities.accounts

/**
 * Al utilizar data class se implementa de forma automática el método equals, toString, copy y hashCode,
 * teniendo en cuenta las propiedades declaradas en el constructuro primario/principal
 */
data class User(var name: String, var surname: String, var email: String) : Comparable<User> {
    override fun compareTo(other: User): Int {
        return name.compareTo(other.name)
    }
}
