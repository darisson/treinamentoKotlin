package com.example.darisson.treinamentokotlin.modules.network.auth

import com.example.darisson.treinamentokotlin.modules.model.Data
import com.example.darisson.treinamentokotlin.modules.model.Usuario
import com.example.darisson.treinamentokotlin.modules.model.UsuarioWrapper
import com.example.darisson.treinamentokotlin.modules.network.BaseNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

object AutenticacaoNetwork : BaseNetwork(){

    val autenticacaoAPI by lazy {
        getRetrofit().create(AutenticacaoAPI::class.java)
    }

    fun entrar(
            usuario: UsuarioWrapper,
            onSuccess: (response: Response<Data<Usuario>>) -> Unit,
            onError: () -> Unit
    ){

        autenticacaoAPI.entrar(usuario)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->
                            if (response.isSuccessful) {
                                onSuccess(response)
                            } else {
                                onError()
                            }
                        },
                        {
                             onError()
                        }
                )
    }

    fun criarUsuario(
            usuario: UsuarioWrapper,
            onSucess: (usuario: Usuario) -> Unit,
            onError: () -> Unit
    ) {

        autenticacaoAPI.criarUsuario(usuario)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->

                            val usuario = response.data
                            usuario?.let {
                                onSucess(it)
                            }
                },
                        {
                            onError
                        }
                )
    }

    fun sair(
            usuario: Usuario,
            onSuccess:() -> Unit,
            onError: () -> Unit
    ) {
        autenticacaoAPI.sair(usuario.uid, usuario.client, usuario.accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onSuccess()
                }, {
                    onError()
                })
    }
}