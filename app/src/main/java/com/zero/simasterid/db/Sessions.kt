package com.zero.simasterid.db

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.zero.simasterid.BuildConfig


@SuppressLint("CommitPrefEdits")
class Sessions(context: Context) {
    companion object {
        var PREF_NAME = BuildConfig.APPLICATION_ID + ".session"
        const val root: String = "root"
    }

    var pref: SharedPreferences
    var editor: SharedPreferences.Editor? = null

    var context: Context? = null
    val PRIVATE_MODE: Int = 0

    init {
        this.context = context
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    fun putString(key: String, value: String) {
        editor!!.putString(key, value)
        editor!!.commit()
    }

    fun putBoolean(key: String, value: Boolean) {
        editor!!.putBoolean(key, value)
        editor!!.commit()
    }

    fun getString(key: String): String {
        return pref.getString(key, "").toString()
    }

    fun getBoolean(key: String): Boolean {
        return pref.getBoolean(key, false)
    }


    fun isRoot(): Boolean {
        return getBoolean(root)
    }

    fun Logout() {
        editor!!.clear()
        editor!!.commit()
    }
}