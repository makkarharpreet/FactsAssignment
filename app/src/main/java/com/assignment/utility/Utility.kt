package com.assignment.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

/**
 * @author Harpreet Singh
 */
object Utility {

    /**
     * this method is used to load Images in ImageView
     */
    fun ImageView.loadImage(url: String?) {
        Glide.with(context).load(url).into(this)
    }

    /**
     * this method is used to showing error message in Snackbar
     */
    fun View.showErrorMsg(message: String) {
        Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
    }

    /**
     * this method is used to check internet connectivity
     */
    @Suppress("DEPRECATION")
    fun isNetworkAvailable(context:Context?): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw      = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }
}