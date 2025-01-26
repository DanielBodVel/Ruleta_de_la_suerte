package com.example.ruletadelasuerte.utils

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class LanguageChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "com.example.ruletadelasuerte.LANGUAGE_CHANGED") {
            val activity = context as? Activity
            activity?.recreate() // Reinicia la actividad actual
        }
    }
}
