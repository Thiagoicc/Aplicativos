package br.unifor.cct.bluepocket.activity.Receita

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.unifor.cct.bluepocket.R
import br.unifor.cct.bluepocket.entity.Receita

class ListaAdapterReceita(val context: Context, val receita: List<Receita>):RecyclerView.Adapter<ListaAdapterReceita.DespesaListViewHolder>() {
    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    class DespesaListViewHolder(view: View): RecyclerView.ViewHolder(view){
        var despesaNome: TextView = view.findViewById(R.id.item_textField_nomeDespesa)
        var despesaValor: TextView = view.findViewById(R.id.item_textView_valor)
        var despesaTipo: TextView = view.findViewById(R.id.item_textView_tipo)
        var despesaData: TextView = view.findViewById(R.id.item_textView_data)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DespesaListViewHolder {

        val view = layoutInflater.inflate(R.layout.item_recyclerview,parent,false)
        val holder =
            DespesaListViewHolder(
                view
            )
        return holder

    }

    override fun getItemCount(): Int {
        return receita.size
    }

    override fun onBindViewHolder(holder: DespesaListViewHolder, position: Int) {
        holder.despesaNome.text = receita[position].nome
        holder.despesaValor.text = receita[position].valor.toString()
        holder.despesaTipo.text = receita[position].tipo
        holder.despesaData.text = receita[position].data.get(0) + "" + receita[position].data.get(1) + "/" + receita[position].data.get(2) + "" + receita[position].data.get(3) + "/" +
                receita[position].data.get(4) + "" + receita[position].data.get(5) + "" + receita[position].data.get(6) + "" + receita[position].data.get(7)


    }
}