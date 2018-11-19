package com.example.darisson.treinamentokotlin.modules.view.adapter

import android.content.Context
import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import com.example.darisson.treinamentokotlin.R
import com.example.darisson.treinamentokotlin.modules.business.ContatoBusiness
import com.example.darisson.treinamentokotlin.modules.database.ContatoDatabase
import com.example.darisson.treinamentokotlin.modules.model.Contato
import com.example.darisson.treinamentokotlin.modules.view.activity.DetalhesContatoActivity
import com.example.darisson.treinamentokotlin.modules.view.activity.DetalhesContatoActivity.Companion.CONTATO_ID_EXTRA
import com.example.darisson.treinamentokotlin.modules.view.activity.NovoContatoActivity
import com.example.darisson.treinamentokotlin.modules.view.activity.NovoContatoActivity.Companion.IS_EDIT
import com.example.darisson.treinamentokotlin.modules.view.item.ContatoItemView

class ContatoAdapter {

    class ContatoAdapter(var contatos: List<Contato>, val context: Context) : RecyclerView.Adapter<ContatoItemView>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoItemView {
            return ContatoItemView(LayoutInflater.from(context).inflate(R.layout.item_contato, parent, false))
        }

        override fun getItemCount(): Int {
            return contatos.size
        }

        override fun onBindViewHolder(contatoItemView: ContatoItemView, postion: Int) {

            val contato: Contato = contatos[postion]
            val id = contato.id

            contatoItemView.bind(contato)

            onClick(contatoItemView, id)

            onLongClick(contatoItemView, id)
        }

        // TODO: passar configuracao da view holder para detro da viewholder
        private fun onClick(holder: ContatoItemView, id: Int?) {
            holder.itemView.setOnClickListener {

                val intent = Intent(context, DetalhesContatoActivity::class.java)
                intent.putExtra(CONTATO_ID_EXTRA, id)
                context.startActivity(intent)
            }
        }

        private fun onLongClick(holder: ContatoItemView, id: Int?) {
            holder.itemView.setOnLongClickListener { itemView ->

                val popupMenu = PopupMenu(context, itemView)
                popupMenu.inflate(R.menu.item_menu_contato)

                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.apagarContato -> apagarContato(id, holder.itemView)
                        R.id.editarContato -> editarContato(id)
                        else -> false
                    }
                }
                popupMenu.show()
                true
            }
        }

        private fun editarContato(id: Int?): Boolean {

            if (id != null) {

                val intent = Intent(context, NovoContatoActivity::class.java)
                intent.putExtra(CONTATO_ID_EXTRA, id)
                intent.putExtra(IS_EDIT, true)
                context.startActivity(intent)
            }
            return true
        }

        private fun apagarContato(id: Int?, view: View): Boolean {


            id?.let {
                ContatoBusiness.apagarContato(id, {
                    refreshContatos()
                    Snackbar.make(view, R.string.sucesso_apagar_contato, Snackbar.LENGTH_SHORT).show()
                }, {
                    Snackbar.make(view, R.string.erro_apagar_contato, Snackbar.LENGTH_SHORT).show()
                })
            }
            return true
        }

        fun refreshContatos() {
            this.contatos = ContatoDatabase.getContatos()
            notifyDataSetChanged()
        }
    }
}  