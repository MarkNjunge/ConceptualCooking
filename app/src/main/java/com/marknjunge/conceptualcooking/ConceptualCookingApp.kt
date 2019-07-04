package com.marknjunge.conceptualcooking

import android.app.Application
import timber.log.Timber

@Suppress("unused")
class ConceptualCookingApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String {
                val filename = element.fileName.substringBefore(".")
                return "Timber/$filename.${element.methodName}(Ln${element.lineNumber})"
            }
        })
    }
}