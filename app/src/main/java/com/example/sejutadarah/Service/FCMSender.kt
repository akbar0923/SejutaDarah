package com.example.sejutadarah.Service


import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody


class FCMSender {
    var client: OkHttpClient = OkHttpClient()

    /*
     * Method to send notification to the application
     * */
    fun send(message: String, callback: Callback) {
        val mediaType = "application/json; charset=utf-8".toMediaType()

        val reqBody = message.toRequestBody(mediaType)

        val request: Request = Request.Builder()
            .url(FCM_URL)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", KEY_STRING)
            .post(reqBody)
            .build()
        val call: Call = client.newCall(request)
        call.enqueue(callback)

    }

    companion object {
        /*
     * URL where we request to send notification and the key to send notification using admin sdk
     * */
        private const val FCM_URL = "https://fcm.googleapis.com/fcm/send"
        private const val KEY_STRING = "key=AIzaSyACu7dpQQLNX9IPnN1FUGR7ub4hIZ3A_ag"
    }
}
