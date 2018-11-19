package com.example.darisson.treinamentokotlin.modules.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.example.darisson.treinamentokotlin.R
import com.example.darisson.treinamentokotlin.modules.business.AutenticacaoBusiness
import com.example.darisson.treinamentokotlin.modules.model.Usuario
import com.example.darisson.treinamentokotlin.modules.model.UsuarioWrapper
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        clickEntrar()
        clickCriarConta()

    }

    private fun dadosUsuario(): UsuarioWrapper{
        val usuario = UsuarioWrapper().apply {
            email = txtEmail.text.toString()
            password = txtSenha.text.toString()
            password_confirmation = txtSenha.text.toString()
        }
        return usuario
    }

    private fun entrarAgenda(){
        // TODO: ver activity com anko
        startActivity(Intent(this, ContatosActivity::class.java))
    }

    private fun entrar(usuario: UsuarioWrapper) {

        AutenticacaoBusiness.entrar(usuario, {
            Snackbar.make(btnEntrar, R.string.msg_entrando, Snackbar.LENGTH_SHORT).show()
            entrarAgenda()
        }, { mensagemErro ->
            Snackbar.make(btnEntrar, mensagemErro, Snackbar.LENGTH_SHORT).show()
        })
    }

    private fun clickEntrar(){
        btnEntrar.setOnClickListener{
            val usuario = dadosUsuario()
            entrar(usuario)
        }
    }

    private fun clickCriarConta(){
        btnCriarConta.setOnClickListener{
            attemptCriarConta()
        }
    }

    private fun attemptCriarConta() {

        val email = txtEmail.text.toString()
        val password = txtSenha.text.toString()
        val password_confirmation = txtSenha.text.toString()

        // valida os dados

        if (email.isEmpty()) {
            // enviar mensagem de erro
            return
        }

        if (password.isEmpty()) {
            // enviar mensagem de erro
            return
        }

        if (password_confirmation != password) {
            // enviar mensagem de erro
            return
        }

        // cria o usuario

        val user = UsuarioWrapper().apply {
            this.email = email
            this.password = password
            this.password_confirmation = password_confirmation
        }

        AutenticacaoBusiness.criarUsuario(user,
                onSuccess = {
                    Snackbar.make(btnCriarConta, R.string.conta_criada, Snackbar.LENGTH_SHORT).show()
                },
                onError = { _ ->
                    Snackbar.make(btnCriarConta, R.string.erro_criar_conta, Snackbar.LENGTH_SHORT).show()
                }
        )
    }

}