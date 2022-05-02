package com.example.doctormobile.util

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

val pendingCollectionReference by lazy {
    FirebaseFirestore
        .getInstance()
        .collection("Pending")
}

val pendingQuery by lazy {
    pendingCollectionReference
        .orderBy("time", Query.Direction.DESCENDING)
}


val assignedCollectionReference by lazy {
    FirebaseFirestore
        .getInstance()
        .collection("Assigned")
}

val assignedQuery by lazy {
    assignedCollectionReference
        .orderBy("time", Query.Direction.DESCENDING)
}


val completedCollectionReference by lazy {
    FirebaseFirestore
        .getInstance()
        .collection("Completed")
}

val completedQuery by lazy {
    completedCollectionReference
}

val deletedCollectionReference by lazy {
    FirebaseFirestore
        .getInstance()
        .collection("Deleted")
}

val deletedQuery by lazy {
    deletedCollectionReference
        .orderBy("time", Query.Direction.DESCENDING)
}

val orderRefQuery by lazy {
    FirebaseDatabase.getInstance("https://nuhealthcenter-651ef-default-rtdb.asia-southeast1.firebasedatabase.app/ ").reference.child("Order")
}

val assignedRefQuery by lazy {
    FirebaseDatabase.getInstance("https://nuhealthcenter-651ef-default-rtdb.asia-southeast1.firebasedatabase.app/").reference.child("Assigned")
}

val completedRefQuery by lazy {
    FirebaseDatabase.getInstance("https://nuhealthcenter-651ef-default-rtdb.asia-southeast1.firebasedatabase.app/").reference.child("Completed")
}

val pendingRefQuery by lazy {
    FirebaseDatabase.getInstance("https://nuhealthcenter-651ef-default-rtdb.asia-southeast1.firebasedatabase.app/").reference.child("Pending")
}
