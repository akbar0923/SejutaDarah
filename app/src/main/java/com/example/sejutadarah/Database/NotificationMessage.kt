package com.example.sejutadarah.Database

class NotificationMessage (token: String, body: String) {
    var message = "{" +
            "  \"to\": \"$token\"," +
            "  \"data\": {" +
            "       \"body\":\"$body\"," +
            "   }," +
            "  \"priority\": \"high\"" +
            "}"
}