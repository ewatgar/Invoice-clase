package com.murray.account.ui.signup.usecase

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murray.entities.accounts.TypeAccounts
import com.murray.entities.accounts.UserSignUp
import com.murray.entities.accounts.VisibilityAccounts
import com.murray.network.Resource
import com.murray.repositories.UserSignUpRepository
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var password2 = MutableLiveData<String>()
    var typeAccount = MutableLiveData<TypeAccounts>()
    var visibilityAccount = MutableLiveData<VisibilityAccounts>()

    var state = MutableLiveData<SignUpState>()

    fun validateCredentials() {

        when {
            TextUtils.isEmpty(email.value) -> state.value = SignUpState.EmailEmptyError
            TextUtils.isEmpty(password.value) -> state.value = SignUpState.PasswordEmptyError
            TextUtils.isEmpty(password2.value) -> state.value = SignUpState.PasswordEmptyError2
            !isEqualPasswords(password.value!!, password2.value!!) -> state.value =
                SignUpState.PasswordsNotEquals

            else -> {
                //1 añadimos el usuario al repositorio con corrutina
                //corrutina
                viewModelScope.launch {
                    val userSignUp = UserSignUp(
                        password.value!!,
                        typeAccount.value!!,
                        visibilityAccount.value!!,
                        "Name",
                        "Surname",
                        email.value!!
                    )
                    val result = UserSignUpRepository.register(userSignUp)

                    when(result){
                        is Resource.Error -> state.value = SignUpState.EmailDuplicatedError
                        is Resource.Success<*> -> state.value = SignUpState.Success(userSignUp)
                    }
                }

            }
        }

    }

    private fun isEqualPasswords(pass1: String, pass2: String): Boolean {
        return pass1 == pass2
    }

    fun getState(): LiveData<SignUpState> {
        return state
    }
}