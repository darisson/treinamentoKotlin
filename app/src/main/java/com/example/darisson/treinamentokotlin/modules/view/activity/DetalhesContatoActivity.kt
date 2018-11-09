package com.example.darisson.treinamentokotlin.modules.view.activity

import android.os.Bundle
import android.view.MenuItem
import com.example.darisson.treinamentokotlin.R
import com.example.darisson.treinamentokotlin.modules.database.ContatoDatabase
import com.example.darisson.treinamentokotlin.modules.model.Contato
import com.example.darisson.treinamentokotlin.modules.view.activity.ContatosActivity.Companion.ID_CONTATO
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_contato_detalhes.*
import java.text.SimpleDateFormat
import java.util.*

class DetalhesContatoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contato_detalhes)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id: Int = intent.extras.getInt(ID_CONTATO)

        val contato = ContatoDatabase.getContato(id)

        preencherDadosContato(contato)
    }

    private fun preencherDadosContato(contato: Contato?) {

        contato?.let { contato ->
            txtDetalheNome.text = contato.name
            txtDetalheEmail.text = contato.email
            txtDetalheNumero.text = contato.phone

            val birth = getDate(contato.birth)
            txtDetalheBirth.text = birth

            Picasso.get().load(contato.picture.toString()).placeholder(R.drawable.ic_person_black_24dp).fit().into(imgDetalheContato)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getDate(timestamp: Long?): String {

        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val data = Date(timestamp.toString().toLong())

        return dateFormat.format(data)
    }


}