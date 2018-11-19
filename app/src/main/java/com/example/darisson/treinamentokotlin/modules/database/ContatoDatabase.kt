package com.example.darisson.treinamentokotlin.modules.database

import com.example.darisson.treinamentokotlin.modules.model.Contato
import io.realm.Realm

object ContatoDatabase {

    fun apagarContatos() {

        Realm.getDefaultInstance().use { realm ->
            realm.beginTransaction()
            realm.delete(Contato::class.java)
            realm.commitTransaction()
        }
    }

    fun apagarContato(id: Int) {

        Realm.getDefaultInstance().use { realm ->
            realm.beginTransaction()
            realm.where(Contato::class.java).equalTo("id", id).findFirst()?.deleteFromRealm()
            realm.commitTransaction()
        }
    }

    fun salvarContato(contato: Contato) {

        Realm.getDefaultInstance().use { realm ->
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(contato)
            realm.commitTransaction()
        }
    }

    fun salvarContatos(contatos: List<Contato>) {

        apagarContatos()
        Realm.getDefaultInstance().use { realm ->
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(contatos)
            realm.commitTransaction()
        }
    }

    fun getContato(id: Int): Contato? {

        Realm.getDefaultInstance().use { realm ->
            val contato = realm.where(Contato::class.java).equalTo("id", id).findFirst()

            return realm.copyFromRealm(contato)
        }
    }

//    fun editarContato(novoContato: Contato, onSuccess: () -> Unit) {
//
//        Realm.getDefaultInstance().use { realm ->
//            realm.beginTransaction()
//            realm.copyToRealmOrUpdate(novoContato)
//            realm.commitTransaction()
//            onSuccess()
//        }
//    }

    fun getContatos(): List<Contato> {

        Realm.getDefaultInstance().use { realm ->
            val contatos = realm.where(Contato::class.java).findAll()

            return realm.copyFromRealm(contatos)
        }
    }
}