package com.example.darisson.treinamentokotlin.modules.network.contatos

import com.example.darisson.treinamentokotlin.modules.model.Contato
import com.example.darisson.treinamentokotlin.modules.model.Usuario
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ContatoNetwork {

    val contatoAPI by lazy {
        getRetrofit().create(ContatoAPI::class.java)
    }

    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
                .baseUrl("https://api-agenda-unifor.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    fun criarContato(usuario: Usuario, contato: Contato, onSuccess:(contato: Contato) -> Unit, onError: (msg: String) -> Unit){

        contatoAPI.criarContato(usuario.uid, usuario.client, usuario.accessToken, contato)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ contato ->

                    contato?.let{
                        onSuccess(it)
                    }
                }, {
                    val msg: String = it.message.toString()
                    onError(msg)
                })
    }

    fun listarContatos(usuario: Usuario, onSuccess: (contatos: List<Contato>) -> Unit, onError: (msg: String) -> Unit){
        contatoAPI.listarContatos(usuario.uid, usuario.client, usuario.accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ contatos ->

                    contatos?.let {
                        onSuccess(it)
                    }
                },{
                    val msg: String = it.message.toString()
                    onError(msg)
                })
    }

    fun apagarContato(usuario: Usuario, id: Int, onSuccess: (id: Int) -> Unit, onError: (msg: String) -> Unit) {

        contatoAPI.apagarContato(usuario.uid, usuario.client, usuario.accessToken, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onSuccess(id)
                }, {
                    val msg: String = it.message.toString()
                    onError(msg)
                })
    }

    fun editarContato(usuario: Usuario, contato: Contato?, id: Int?, onSuccess: (contato: Contato) -> Unit, onError: () -> Unit) {

        contatoAPI.editarContato(usuario.uid, usuario.client, usuario.accessToken, contato, id.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onSuccess(it)
                }, {
                    onError()
                })
    }

}