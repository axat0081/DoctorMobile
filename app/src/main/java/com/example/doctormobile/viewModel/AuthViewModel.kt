package com.example.doctormobile.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val app: Application,
    private val state: SavedStateHandle
): AndroidViewModel(app) {
   var loginEmail = state.get<String>("login_email")?:""
    set(value) {
        field = value
        state.set("login_email",loginEmail)
    }
    var registerEmail = state.get<String>("register_email")?:""
        set(value) {
            field = value
            state.set("login_email",registerEmail)
        }
    var loginPassword = state.get<String>("login_password")?:""
        set(value) {
            field = value
            state.set("login_email",loginPassword)
        }
    var registerPassword = state.get<String>("register_password")?:""
        set(value) {
            field = value
            state.set("login_email",registerPassword)
        }


}