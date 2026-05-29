package com.example.navbackstackentryexample

import android.app.Application
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get().lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                Log.d("APP_LIFECYCLE", "Application onCreate")
            }

            override fun onStart(owner: LifecycleOwner) {
                Log.d("APP_LIFECYCLE", "Application onStart")
            }

            override fun onResume(owner: LifecycleOwner) {
                Log.d("APP_LIFECYCLE", "Application onResume")
            }

            override fun onPause(owner: LifecycleOwner) {
                Log.d("APP_LIFECYCLE", "Application onPause")
            }

            override fun onStop(owner: LifecycleOwner) {
                Log.d("APP_LIFECYCLE", "Application onStop")
            }

            override fun onDestroy(owner: LifecycleOwner) {
                Log.d("APP_LIFECYCLE", "Application onDestroy")
            }
        })
    }

    override fun onTerminate() {
        super.onTerminate()
        Log.d("APP_LIFECYCLE", "Application onTerminate")
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.d("APP_LIFECYCLE", "Application onLowMemory")
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Log.d("APP_LIFECYCLE", "Application onTrimMemory level=$level")
    }
}
