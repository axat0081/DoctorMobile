package com.example.doctormobile.viewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doctormobile.MainActivity
import com.example.doctormobile.models.MedOrder
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class OrderMedicinesViewModel @Inject constructor(
    val state: SavedStateHandle,
    @Named("Order") private val orderRef: DatabaseReference,
) : ViewModel() {
    var medList = ArrayList<String>()
    var orderedMeds = ArrayList<String>()
    var name = state.get<String>("name") ?: ""
        set(value) {
            field = value
            state.set("name", name)
        }
    var enrollNo = state.get<String>("enrollNo") ?: ""
        set(value) {
            field = value
            state.set("enrollNo", enrollNo)
        }

    init {
        var i = 1
        while (i <= 20) {
            medList.add("Medicine$i")
            i++
        }
        orderedMeds.clear()
    }

    private val orderChannel = Channel<OrderEvent>()
    val orderFlow = orderChannel.receiveAsFlow()

    fun onMedClick(name: String) {
        if (orderedMeds.contains(name)) {
            orderedMeds.remove(name)
        } else {
            orderedMeds.add(name)
        }
    }

    fun onOrderClick() {
        for (med in orderedMeds) {
            Log.e("MEDS", med)
        }
        val id = orderRef.push().key!!
        orderRef.child(id).setValue(
            MedOrder(
                id = id,
                email = MainActivity.email,
                medList = orderedMeds
            )
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                viewModelScope.launch {
                    orderChannel.send(OrderEvent.OrderSuccess("Order place successfully"))
                }
            } else {
                viewModelScope.launch {
                    orderChannel.send(
                        OrderEvent.OrderFailure(
                            task.exception!!.localizedMessage ?: "An error occurred"
                        )
                    )
                }
            }
            orderedMeds.clear()
        }
    }

    sealed class OrderEvent {
        data class OrderSuccess(val msg: String) : OrderEvent()
        data class OrderFailure(val msg: String) : OrderEvent()
    }
}