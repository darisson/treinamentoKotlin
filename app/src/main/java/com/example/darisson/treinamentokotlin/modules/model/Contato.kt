package com.example.darisson.treinamentokotlin.modules.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

open class Contato : RealmObject(), Serializable {
    @PrimaryKey
    var id: Int = 0

    var name: String? = ""
    var birth: Long? = 0
    var email: String? = ""
    var phone: String? = ""
    var picture: String? = ""

    fun getBirthDate(): String {

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("pt_BR"))
        val data = Date(birth.toString().toLong())

        return dateFormat.format(data)
    }
}