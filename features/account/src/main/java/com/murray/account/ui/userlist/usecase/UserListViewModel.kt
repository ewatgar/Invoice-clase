package com.murray.account.ui.userlist.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murray.entities.accounts.User
import com.murray.network.ResourceList
import com.murray.repositories.UserRepository
import kotlinx.coroutines.launch
import java.util.Collections

class UserListViewModel : ViewModel() {

    private var state = MutableLiveData<UserListState>()

    fun getState(): LiveData<UserListState> {
        return state
    }


    fun getUserList() {
        viewModelScope.launch {

            state.value = UserListState.Loading(true)
            val result = UserRepository.getUserList()
            state.value = UserListState.Loading(false)

            when (result) {
                is ResourceList.Success<*> ->
                {
                    var dataset = result.data as ArrayList<User>
                    //Collections.sort(dataset) Java
                    dataset.sort() //Kotlin orden NATURAL
                    state.value = UserListState.Success(dataset)
                }

                is ResourceList.Error -> state.value = UserListState.NoDataError
            }
        }
    }
}
