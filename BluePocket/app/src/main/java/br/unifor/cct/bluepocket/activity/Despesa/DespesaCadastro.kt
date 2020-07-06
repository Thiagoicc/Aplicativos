package br.unifor.cct.bluepocket.activity.Despesa

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import br.unifor.cct.bluepocket.R
import br.unifor.cct.bluepocket.activity.MainActivity
import br.unifor.cct.bluepocket.activity.SpinnerAdapter
import br.unifor.cct.bluepocket.entity.Despesa
import br.unifor.cct.bluepocket.entity.Tipo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_despesa_cadastro.*
import java.lang.NumberFormatException


class DespesaCadastro : AppCompatActivity(), View.OnClickListener {

    private lateinit var buttonCadastro: Button
    private lateinit var nomeCadastro: TextView
    private lateinit var valorCadastro: TextView
    private lateinit var voltar: ImageButton

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDataBase: FirebaseDatabase

    var listaTiposDespesas = mutableListOf<Tipo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_despesa_cadastro)

        mDataBase = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance()

        buttonCadastro = findViewById(R.id.cadDespesa_button)
        nomeCadastro = findViewById(R.id.cadDespesa_nome)
        valorCadastro = findViewById(R.id.cadDespesa_valor)
        voltar = findViewById(R.id.cadDespesa_voltar)

        buttonCadastro.setOnClickListener(this)
        nomeCadastro.setOnClickListener(this)
        valorCadastro.setOnClickListener(this)
        voltar.setOnClickListener(this)

        cadDespesa_spinner_dia.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.dia))

        cadDespesa_spinner_mes.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.mes))

        cadDespesa_spinner_ano.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.ano))

        val adapter = SpinnerAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listaTiposDespesas
        )

        val spinner = findViewById<Spinner>(R.id.cadDespesa_spinner)
        spinner.adapter = adapter


    }

    override fun onStart() {
        super.onStart()

        val tipoRef = mDataBase.getReference("tipos")
        val query = tipoRef.addValueEventListener(object:
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach{
                    val category = it.getValue(Tipo::class.java)

                    if(category!!.userId == mAuth.currentUser!!.uid || category!!.prepopulado == "Prepopulado") {
                        if (category!!.tipo == "Despesa") {

                            listaTiposDespesas.add(category!!)
                        }
                    }
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.cadDespesa_button -> {
                teste()
            }
            R.id.cadDespesa_voltar -> {
                val it = Intent(this, MainActivity::class.java)
                startActivity(it)
            }
        }
    }

    private fun teste() {
        try {
            if (nomeCadastro.text.isEmpty() || valorCadastro.text.isEmpty()) {
                if (nomeCadastro.text.isEmpty()) {
                    nomeCadastro.error = "Insira um nome válido"
                }
                if (valorCadastro.text.isEmpty()) {
                    valorCadastro.error = "Insira um valor válido"
                }
            } else {
                val despesa = Despesa(
                    nome = cadDespesa_nome.text.toString(),
                    tipo = listaTiposDespesas[cadDespesa_spinner.selectedItemId.toInt()].nome,
                    userId = mAuth.currentUser!!.uid,
                    data = cadDespesa_spinner_dia.selectedItem.toString() + cadDespesa_spinner_mes.selectedItem.toString() + cadDespesa_spinner_ano.selectedItem.toString(),
                    valor = cadDespesa_valor.text.toString().toDouble()
                )

                val despesaRef = mDataBase.getReference("despesas")
                val despesaId = despesaRef.push().key

                despesaRef.child(despesaId!!).setValue(despesa).addOnSuccessListener {
                    val dialog = AlertDialog.Builder(this)
                        .setMessage("Despesa cadastrada com sucesso!")
                        .setTitle("BluePocket")
                        .setCancelable(false)
                        .setNeutralButton(
                            "OK",
                            DialogInterface.OnClickListener { dialog, id ->
                                dialog.dismiss()
                            }).create()
                    dialog.show()
                    nomeCadastro.text = null
                    valorCadastro.text = null
                }
            }
        } catch (ex: NumberFormatException)  {
            val dialog = AlertDialog.Builder(this)
                .setMessage("Formato de Valor Errado\nFormato exemplo: 1834.22")
                .setTitle("BluePocket")
                .setCancelable(false)
                .setNeutralButton(
                    "OK",
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.dismiss()
                    }).create()
            dialog.show()
        }
    }
}
