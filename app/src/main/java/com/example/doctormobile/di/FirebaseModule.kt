package com.example.doctormobile.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    @Named("Pending")
    fun providesPendingRef(): DatabaseReference =
        FirebaseDatabase.getInstance().reference.child("Pending")

    @Provides
    @Singleton
    @Named("Assigned")
    fun providesAssignedRef(): DatabaseReference =
        FirebaseDatabase.getInstance().reference.child("Assigned")

    @Provides
    @Singleton
    @Named("Completed")
    fun providesCompletedRef(): DatabaseReference =
        FirebaseDatabase.getInstance().reference.child("Completed")

    @Provides
    @Singleton
    @Named("Order")
    fun providesOrderRef(): DatabaseReference =
        FirebaseDatabase.getInstance().reference.child("Order")

    @Provides
    @Singleton
    @Named("Deleted")
    fun providesDeletedRef(): CollectionReference =
        FirebaseFirestore.getInstance().collection("Deleted")

}