package uz.gita.appbuilderuser.app

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application(){

    override fun onCreate() {
        super.onCreate()
        instent = this
    }

    companion object{
        lateinit var instent:App
    }
}
