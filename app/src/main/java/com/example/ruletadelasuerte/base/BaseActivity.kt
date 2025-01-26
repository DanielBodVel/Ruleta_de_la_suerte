package com.example.ruletadelasuerte.base

import android.content.Context
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import com.example.ruletadelasuerte.utils.LanguageChangeReceiver
import com.example.ruletadelasuerte.utils.LocaleManager
import com.example.ruletadelasuerte.utils.updateLocale

abstract class BaseActivity : AppCompatActivity() {
    private val languageChangeReceiver = LanguageChangeReceiver()

    override fun attachBaseContext(newBase: Context?) {
        val localeManager = LocaleManager(newBase!!)
        val context = updateLocale(newBase, localeManager.getLocale())
        super.attachBaseContext(context)
    }


    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter("com.example.ruletadelasuerte.LANGUAGE_CHANGED")
        registerReceiver(languageChangeReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(languageChangeReceiver)
    }
}
