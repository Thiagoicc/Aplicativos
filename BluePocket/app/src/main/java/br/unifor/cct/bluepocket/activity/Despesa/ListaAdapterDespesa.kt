package br.unifor.cct.bluepocket.activity.Despesa

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.unifor.cct.bluepocket.R
import br.unifor.cct.bluepocket.entity.Despesa

class ListaAdapterDespesa(val context: Context, val despesa: List<Despesa>):RecyclerView.Adapter<ListaAdapterDespesa.DespesaListViewHolder>() {

    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    class DespesaListViewHolder(view: View):RecyclerView.ViewHolder(view){
         var despesaNome:TextView = view.findViewById(R.id.item_textField_nomeDespesa)
        var despesaValor:TextView = view.findViewById(R.id.item_textView_valor)
        var despesaTipo:TextView = view.findViewById(R.id.item_textView_tipo)
        var despesaData:TextView = view.findViewById(R.id.item_textView_data)

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
        return despesa.size
    }

    override fun onBindViewHolder(holder: DespesaListViewHolder, position: Int) {
        holder.despesaNome.text = despesa[position].nome
        holder.despesaValor.text = despesa[position].valor.toString()
        holder.despesaTipo.text = despesa[position].tipo
        holder.despesaData.text = despesa[position].data.get(0) + "" + despesa[position].data.get(1) + "/" + despesa[position].data.get(2) + "" + despesa[position].data.get(3) + "/" +
                                    despesa[position].data.get(4) + "" + despesa[position].data.get(5) + "" + despesa[position].data.get(6) + "" + despesa[position].data.get(7)


    }
}