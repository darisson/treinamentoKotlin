package com.example.darisson.treinamentokotlin.modules.model

import com.google.gson.annotations.SerializedName

class UsuarioWrapper {

    @SerializedName("email")
    var email: String? = ""

    @SerializedName("password")
    var password: String? = ""

    @SerializedName("password_confirmation")
    var password_confirmation: String? = ""

}