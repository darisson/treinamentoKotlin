package com.example.darisson.treinamentokotlin.modules.database

import com.example.darisson.treinamentokotlin.modules.model.Usuario
import io.realm.Realm

object AutenticacaoDatabase {

    fun salvaUsuario(usuario: Usuario, onSuccess:() -> Unit){

        Realm.getDefaultInstance().use { realm ->
            realm.beginTransaction()
            realm.copyToRealm(usuario)
            realm.commitTransaction()
            onSuccess()
        }
    }

    fun limparBanco(){
        Realm.getDefaultInstance().use { realm ->
            realm.beginTransaction()
            realm.deleteAll()
            realm.commitTransaction()

        }
    }

    fun getUsuario(): Usuario? {
        Realm.getDefaultInstance().use { realm ->
            val usuario: Usuario? = realm.where(Usuario::class.java).findFirst()

            usuario?.let {
                return realm.copyFromRealm(usuario)
            }
            return null
        }
    }
}