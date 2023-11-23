package uz.gita.appbuilderuser.di

import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @[Provides Singleton]
    fun provideFirebaseDataBase(): FirebaseDatabase = Firebase.database

    @[Provides Singleton]
    fun provideRealtimeDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @[Provides Singleton]
    fun provideFireStrorege(): FirebaseFirestore = FirebaseFirestore.getInstance()
}