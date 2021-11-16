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
    fun providesDatabaseReference(): DatabaseReference = FirebaseDatabase.getInstance().reference

    @Provides
    @Singleton
    @Named("Pending")
    fun providesPendingRef(): CollectionReference =
        FirebaseFirestore.getInstance().collection("Pending")

    @Provides
    @Singleton
    @Named("Assigned")
    fun providesAssignedRef(): CollectionReference =
        FirebaseFirestore.getInstance().collection("Assigned")

    @Provides
    @Singleton
    @Named("Completed")
    fun providesCompletedRef(): CollectionReference =
        FirebaseFirestore.getInstance().collection("Completed")

    @Provides
    @Singleton
    @Named("Deleted")
    fun providesDeletedRef(): CollectionReference =
        FirebaseFirestore.getInstance().collection("Deleted")

    @Provides
    @Singleton
    @Named("Order")
    fun providesOrderRef(db: DatabaseReference): DatabaseReference =
        db.child("Order")


}