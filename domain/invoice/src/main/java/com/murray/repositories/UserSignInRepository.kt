package com.murray.repositories

import com.murray.entities.accounts.TypeAccounts
import com.murray.entities.accounts.UserSignUp
import com.murray.entities.accounts.VisibilityAccounts
import com.murray.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserSignInRepository private constructor() {


    companion object {
        var dataset: MutableList<UserSignUp> = mutableListOf()

        init {
            initDatasetUserSignUp()
        }

        fun initDatasetUserSignUp() {
            dataset.add(
                UserSignUp(
                    "1234",
                    TypeAccounts.PUBLIC,
                    VisibilityAccounts.USER,
                    "Eustaquio",
                    "Perez",
                    "eustaquio@email.com"
                )
            )
            dataset.add(
                UserSignUp(
                    "123444355",
                    TypeAccounts.PRIVATE,
                    VisibilityAccounts.ADMIN,
                    "Luis",
                    "Garcia",
                    "luis@email.com"
                )
            )
        }

        suspend fun register(userSignUp: UserSignUp): Resource {
            var resource: Resource
            withContext(Dispatchers.IO) {
                if (checkDuplicatedEmail(userSignUp.email)) {
                    resource = Resource.Error(Exception("Email duplicado"))
                } else {
                    //crear y añadir database aqui?
                    resource = Resource.Success(userSignUp)
                }
            }
            return resource

        }

        fun checkDuplicatedEmail(checkEmail: String): Boolean {
            return dataset.any { it.email == checkEmail }
        }

        fun addUserSignUp(userSignUp: UserSignUp) {
            dataset.add(userSignUp)
        }
    }
}
