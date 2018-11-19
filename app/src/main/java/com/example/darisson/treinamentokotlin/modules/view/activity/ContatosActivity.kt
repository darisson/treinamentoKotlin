package com.example.darisson.treinamentokotlin.modules.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.example.darisson.treinamentokotlin.R
import com.example.darisson.treinamentokotlin.modules.business.AutenticacaoBusiness
import com.example.darisson.treinamentokotlin.modules.business.ContatoBusiness
import com.example.darisson.treinamentokotlin.modules.database.ContatoDatabase
import com.example.darisson.treinamentokotlin.modules.view.activity.NovoContatoActivity.Companion.IS_EDIT
import com.example.darisson.treinamentokotlin.modules.view.adapter.ContatoAdapter
import kotlinx.android.synthetic.main.activity_contatos.*

class ContatosActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contatos)

        setupRecyclerView()
        setupSwipeRefresh()
        setupButton()
    }

    override fun onResume() {
        super.onResume()
        listarContatos()
    }

    override fun onPause() {
        super.onPause()
        swipeRefresh.isRefreshing = false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                logout()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupSwipeRefresh() {
        swipeRefresh.setOnRefreshListener {
            listarContatos()
        }
    }

    private fun setupRecyclerView() {
        listaContatos.layoutManager = LinearLayoutManager(this)
        listaContatos.adapter = ContatoAdapter.ContatoAdapter(ContatoDatabase.getContatos(), this)
    }

    private fun setupButton() {
        btnNovoContato.setOnClickListener {
            val intent = Intent(this, NovoContatoActivity::class.java)
            intent.putExtra(IS_EDIT, false)
            startActivity(intent)
        }
    }

    private fun listarContatos() {

        swipeRefresh.isRefreshing = true

        ContatoBusiness.listarContatos(
                onSuccess = {
                    swipeRefresh.isRefreshing = false
                },
                onError = {
                    Snackbar.make(listaContatos, R.string.erro_listar_contatos, Snackbar.LENGTH_SHORT).show()
                    swipeRefresh.isRefreshing = false
                }
        )
    }

    private fun logout() {
        AutenticacaoBusiness.sair({
            finish()
        }, {
            Snackbar.make(listaContatos, R.string.erro_sair, Snackbar.LENGTH_SHORT).show()
        })
    }

}
