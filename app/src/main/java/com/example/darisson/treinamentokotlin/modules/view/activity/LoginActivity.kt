package com.example.darisson.treinamentokotlin.modules.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.example.darisson.treinamentokotlin.R
import com.example.darisson.treinamentokotlin.modules.business.AutenticacaoBusiness
import com.example.darisson.treinamentokotlin.modules.model.Usuario
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        clickEntrar()
        clickCriarConta()

    }

    override fun onResume() {
        super.onResume()

        txtSenha.text = null
        txtEmail.text = null
    }

    private fun dadosUsuario(): Usuario{
        val usuario = Usuario().apply {
            email = txtEmail.text.toString()
            password = txtSenha.text.toString()
            password_confirmation = txtSenha.text.toString()
        }
        return usuario
    }

    private fun entrarAgenda(){
        startActivity(Intent(this, ContatosActivity::class.java))
    }

    private fun entrar(usuario: Usuario) {

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

    private fun criarConta(usuario: Usuario){
        AutenticacaoBusiness.criarUsuario(usuario, {
            Snackbar.make(btnCriarConta, R.string.conta_criada, Snackbar.LENGTH_SHORT).show()
        },{msgErroCriarConta ->
            Snackbar.make(btnCriarConta, R.string.erro_criar_conta, Snackbar.LENGTH_SHORT).show()
        })
    }

    private fun clickCriarConta(){
        btnCriarConta.setOnClickListener{
            val usuario = dadosUsuario()
            criarConta(usuario)
        }
    }

}