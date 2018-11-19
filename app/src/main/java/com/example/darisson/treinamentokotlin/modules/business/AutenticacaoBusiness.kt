package com.example.darisson.treinamentokotlin.modules.business

import com.example.darisson.treinamentokotlin.R
import com.example.darisson.treinamentokotlin.modules.database.AutenticacaoDatabase
import com.example.darisson.treinamentokotlin.modules.model.Usuario
import com.example.darisson.treinamentokotlin.modules.model.UsuarioWrapper
import com.example.darisson.treinamentokotlin.modules.network.auth.AutenticacaoNetwork

object AutenticacaoBusiness {

    const val ACCESS_TOKEN = "access-token"
    const val UID = "uid"
    const val CLIENT = "client"

    fun entrar(
            usuario: UsuarioWrapper,
            onSuccess: (usuario: Usuario) -> Unit,
            onError: (message: Int) -> Unit
    ) {

        AutenticacaoNetwork.entrar(usuario,
                onSuccess = { response ->

                    val accessToken = response.headers()[ACCESS_TOKEN]!!
                    val uid = response.headers()[UID]!!
                    val client = response.headers()[CLIENT]!!

                    val user = response.body()?.data

                    user?.let {
                        it.accessToken = accessToken
                        it.uid = uid
                        it.client = client

                        AutenticacaoDatabase.salvaUsuario(it)
                        onSuccess(it)
                    }
                },
                onError = {
                    onError(R.string.erro_busca_usuario)
                }
        )
    }

    fun criarUsuario(
            usuario: UsuarioWrapper,
            onSuccess: () -> Unit,
            onError: (message: Int) -> Unit) {

        AutenticacaoNetwork.criarUsuario(usuario,
                onSucess = { user ->
                    user.let {
                    AutenticacaoDatabase.salvaUsuario(it)
                    onSuccess()
            }
        },
                onError = {
                    onError(R.string.erro_busca_usuario)
            }
        )
    }

    fun sair(
            onSuccess: () -> Unit,
            onError: () -> Unit) {

        val usuario = AutenticacaoDatabase.getUsuario()

        usuario?.let {
            AutenticacaoNetwork.sair(usuario,
                    onSuccess = {
                        AutenticacaoDatabase.limparBanco()
                        onSuccess()
            },
                    onError = {
                        onError()
                }
            )
        }
    }
}
