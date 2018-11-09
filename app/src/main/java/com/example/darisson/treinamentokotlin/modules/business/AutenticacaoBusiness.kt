package com.example.darisson.treinamentokotlin.modules.business

import com.example.darisson.treinamentokotlin.R
import com.example.darisson.treinamentokotlin.modules.database.AutenticacaoDatabase
import com.example.darisson.treinamentokotlin.modules.model.Usuario
import com.example.darisson.treinamentokotlin.modules.network.auth.AutenticacaoNetwork
import io.reactivex.internal.util.HalfSerializer.onError
import io.realm.Realm

object AutenticacaoBusiness {

    fun entrar(usuario: Usuario, onSuccess: (usuario:Usuario) -> Unit, onError: (message: Int) -> Unit){
        AutenticacaoNetwork.entrar(usuario, { usuario: Usuario ->
            usuario.let {
                AutenticacaoDatabase.salvaUsuario(it) {
                    onSuccess(it)
                }
            }
        }, {
            onError(R.string.erro_busca_usuario)
        })
    }

    fun criarUsuario(usuario: Usuario, onSuccess: () -> Unit, onError: (message: Int) -> Unit) {

        AutenticacaoNetwork.criarUsuario(usuario, {
            usuario.let {
                AutenticacaoDatabase.salvaUsuario(it) {
                    onSuccess()
                }
            }
        }, {
            onError(R.string.erro_busca_usuario)
        })
    }

    fun sair(onSuccess:() -> Unit, onError:() -> Unit){
        val usuario = AutenticacaoDatabase.getUsuario()

        usuario?.let {
            AutenticacaoNetwork.sair(usuario, {
                AutenticacaoDatabase.limparBanco()
                onSuccess()
            }, {
                onError()
            })
        }
    }
}
