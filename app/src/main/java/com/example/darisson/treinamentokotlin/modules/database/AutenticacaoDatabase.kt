package com.example.darisson.treinamentokotlin.modules.database

import com.example.darisson.treinamentokotlin.modules.model.Usuario
import io.realm.Realm

object AutenticacaoDatabase {

    fun salvaUsuario(usuario: Usuario) {

        Realm.getDefaultInstance().use { realm ->
            realm.beginTransaction()
            realm.copyToRealm(usuario)
            realm.commitTransaction()
        }
    }

    fun limparBanco() {

        Realm.getDefaultInstance().use { realm ->
            realm.beginTransaction()
            realm.deleteAll()
            realm.commitTransaction()
        }
    }

    fun getUsuario(): Usuario? {
        return Realm.getDefaultInstance().use { realm ->
            realm.where(Usuario::class.java)
                    .findFirst()
                    ?.let { realm.copyFromRealm(it) }
        }
    }
}