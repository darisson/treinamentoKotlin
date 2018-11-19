package com.example.darisson.treinamentokotlin.modules.view.activity

import android.os.Bundle
import android.view.MenuItem
import com.example.darisson.treinamentokotlin.R
import com.example.darisson.treinamentokotlin.modules.database.ContatoDatabase
import com.example.darisson.treinamentokotlin.modules.model.Contato
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_contato_detalhes.*
import java.text.SimpleDateFormat
import java.util.*

class DetalhesContatoActivity : BaseActivity() {

    companion object {

        const val CONTATO_ID_EXTRA = "CONTATO_ID_EXTRA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contato_detalhes)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id: Int = intent.extras.getInt(CONTATO_ID_EXTRA)

        val contato = ContatoDatabase.getContato(id)

        preencherDadosContato(contato)
    }

    private fun preencherDadosContato(contato: Contato?) {

        contato?.let {
            txtDetalheNome.text = contato.name
            txtDetalheEmail.text = contato.email
            txtDetalheNumero.text = contato.phone

            val birth = contato.getBirthDate()
            txtDetalheBirth.text = birth

            Picasso.get().load(contato.picture.toString()).placeholder(R.drawable.ic_person_black_24dp).fit().into(imgDetalheContato)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}