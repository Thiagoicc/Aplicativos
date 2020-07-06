package br.unifor.cct.bluepocket.activity

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.unifor.cct.bluepocket.entity.Tipo

class SpinnerAdapter(context: Context, resourceId: Int, val list:List<Tipo>): ArrayAdapter<Tipo>(context, resourceId)   {
    override fun getCount(): Int {
        return list.size
    }

     override fun getItem(position: Int): Tipo? {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getPosition(item: Tipo?): Int {
        return super.getPosition(item)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return spinnerView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return spinnerView(position, convertView, parent)
    }

    private fun spinnerView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getView(position, convertView, parent) as TextView
        label.setTextColor(Color.BLACK)
        label.text = list[position].nome

        return label
    }
}