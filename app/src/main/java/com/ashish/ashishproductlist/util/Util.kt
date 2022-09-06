package com.ashish.ashishproductlist.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.ashish.ashishproductlist.db.DatabaseBuilder
import com.ashish.ashishproductlist.db.DatabaseHelperImpl

class Util {
    companion object {
        const val PRODUCT_ID: String = ""

        fun verifyAvailableNetwork(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        }

        fun getDBHelper(context: Context): DatabaseHelperImpl {
            return DatabaseHelperImpl(DatabaseBuilder.getInstance(context))
        }
    }
}