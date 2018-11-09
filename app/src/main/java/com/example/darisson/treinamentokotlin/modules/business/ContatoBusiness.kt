package com.example.darisson.treinamentokotlin.modules.business

import com.example.darisson.treinamentokotlin.R
import com.example.darisson.treinamentokotlin.modules.database.AutenticacaoDatabase
import com.example.darisson.treinamentokotlin.modules.database.ContatoDatabase
import com.example.darisson.treinamentokotlin.modules.model.Contato
import com.example.darisson.treinamentokotlin.modules.network.contatos.ContatoNetwork

object ContatoBusiness {
    fun listarContatos(onSuccess: () -> Unit, onError: (msg: Int) -> Unit) {

        val usuario = AutenticacaoDatabase.getUsuario()

        usuario?.let {

            ContatoDatabase.apagarContatos()
            ContatoNetwork.listarContatos(usuario, { contatos ->
                ContatoDatabase.salvarContatos(contatos) {
                    onSuccess()
                }
            }, {
                onError(R.string.erro_listar_contatos)
            })
        }
    }

    fun apagarContato(id: Int, onSuccess: () -> Unit, onError: (msg: Int) -> Unit) {

        val usuario = AutenticacaoDatabase.getUsuario()

        usuario?.let {

            ContatoNetwork.apagarContato(usuario, id, {
                ContatoDatabase.apagarContato(id)
                onSuccess()
            }, {
                onError(R.string.erro_apagar_contato)
            })
        }
    }

    fun criarContato(contato: Contato, onSuccess: () -> Unit, onError: (msg: Int) -> Unit) {

        val usuario = AutenticacaoDatabase.getUsuario()

        usuario?.let {
            ContatoNetwork.criarContato(usuario, contato, {
                contato.let {
                    ContatoDatabase.salvarContato(it) {
                        onSuccess()
                    }
                }
            }, {
                onError(R.string.erro_salvar_contato)
            })
        }
    }

    fun editarContato(id: Int?, contato: Contato, onSuccess: () -> Unit, onError: (msg: Int) -> Unit) {

        val usuario = AutenticacaoDatabase.getUsuario()

        usuario?.let {

            ContatoNetwork.editarContato(usuario, contato, id, { contato ->
                ContatoDatabase.editarContato(contato) {
                    onSuccess()
                }
            }, {
                onError(R.string.erro_editar_contato)
            })
        }
    }

    fun isInputPreenchido(contato: Contato?): Boolean {

        return !contato?.name.isNullOrBlank() &&
                !contato?.email.isNullOrBlank() &&
                !contato?.phone.isNullOrBlank() &&
                !contato?.picture.isNullOrBlank() &&
                contato?.birth!! > 0
    }
}