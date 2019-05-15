package org.gdggaborone.stemfestival2019

import android.app.Application
import android.support.multidex.MultiDex

import com.google.firebase.database.FirebaseDatabase

/**
 * Created by dan on 07/10/17.
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)

        // Persist data offline
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

    }


}
