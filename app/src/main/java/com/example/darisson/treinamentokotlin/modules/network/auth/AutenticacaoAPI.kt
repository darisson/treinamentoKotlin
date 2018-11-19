package com.example.darisson.treinamentokotlin.modules.network.auth

import com.example.darisson.treinamentokotlin.modules.model.Data
import com.example.darisson.treinamentokotlin.modules.model.Usuario
import com.example.darisson.treinamentokotlin.modules.model.UsuarioWrapper
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST

interface AutenticacaoAPI {

    @POST("auth/sign_in")
    fun entrar(@Body usuario: UsuarioWrapper): Observable<Response<Data<Usuario>>>

    @POST("/auth")
    fun criarUsuario(@Body usuario: UsuarioWrapper): Observable<Data<Usuario>>

    @DELETE("auth/sign_out")
    fun sair(
            @Header("uid") uid: String,
            @Header("client") client: String,
            @Header("accessToken") accessToken: String
    ): Observable<Data<Usuario>>

}