package com.example.doctormobile.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val app: Application,
    private val state: SavedStateHandle,
    private val mAuth: FirebaseAuth
) : AndroidViewModel(app) {
    var loginEmail = state.get<String>("login_email") ?: ""
        set(value) {
            field = value
            state.set("login_email", loginEmail)
        }
    var registerEmail = state.get<String>("register_email") ?: ""
        set(value) {
            field = value
            state.set("login_email", registerEmail)
        }
    var loginPassword = state.get<String>("login_password") ?: ""
        set(value) {
            field = value
            state.set("login_email", loginPassword)
        }
    var registerPassword = state.get<String>("register_password") ?: ""
        set(value) {
            field = value
            state.set("login_email", registerPassword)
        }

    private val authChannel = Channel<AuthEvent>()
    val authFlow = authChannel.receiveAsFlow()

    fun onRegisterClick() {
        if (registerEmail.isBlank()) {
            viewModelScope.launch {
                authChannel.send(AuthEvent.AuthFailure("Email cannot be empty"))
            }
            return
        }
        if (registerPassword.isBlank()) {
            viewModelScope.launch {
                authChannel.send(AuthEvent.AuthFailure("Password cannot be empty"))
            }
            return
        }
        viewModelScope.launch {
            mAuth.createUserWithEmailAndPassword(registerEmail, registerPassword)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        viewModelScope.launch {
                            authChannel.send(AuthEvent.AuthSuccess("Account Created"))
                        }
                    } else {
                        viewModelScope.launch {
                            authChannel.send(AuthEvent.AuthFailure(it.exception?.localizedMessage.toString()))
                        }
                    }
                }
        }
    }

    fun onLoginClick() {
        if (loginEmail.isBlank()) {
            viewModelScope.launch {
                authChannel.send(AuthEvent.AuthFailure("Email cannot be empty"))
            }
            return
        }
        if (loginPassword.isBlank()) {
            viewModelScope.launch {
                authChannel.send(AuthEvent.AuthFailure("Password cannot be empty"))
            }
            return
        }
        viewModelScope.launch {
            mAuth.signInWithEmailAndPassword(loginEmail, loginPassword)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        viewModelScope.launch {
                            authChannel.send(AuthEvent.AuthSuccess("Login Successful"))
                        }
                    } else {
                        viewModelScope.launch {
                            authChannel.send(AuthEvent.AuthFailure(it.exception?.localizedMessage.toString()))
                        }
                    }
                }
        }
    }

    sealed class AuthEvent {
        data class AuthSuccess(val msg: String) : AuthEvent()
        data class AuthFailure(val msg: String) : AuthEvent()
    }

}