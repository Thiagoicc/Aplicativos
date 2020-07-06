package br.unifor.cct.bluepocket.activity.Receita

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import br.unifor.cct.bluepocket.R
import br.unifor.cct.bluepocket.activity.MainActivity
import br.unifor.cct.bluepocket.activity.SpinnerAdapter
import br.unifor.cct.bluepocket.entity.Receita
import br.unifor.cct.bluepocket.entity.Tipo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_despesa_cadastro.*
import kotlinx.android.synthetic.main.activity_receita_cadastro.*
import java.lang.NumberFormatException

class ReceitaCadastro : AppCompatActivity(), View.OnClickListener {

    private lateinit var buttonCadastro: Button
    private lateinit var nomeCadastro: TextView
    private lateinit var valorCadastro: TextView
    private lateinit var voltar: ImageButton

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDataBase: FirebaseDatabase
    private lateinit var spinner: View

    var listaTiposReceitas = mutableListOf<Tipo>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receita_cadastro)

        mDataBase = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance()

        buttonCadastro = findViewById(R.id.cadReceita_button)
        nomeCadastro = findViewById(R.id.cadReceita_nome)
        valorCadastro = findViewById(R.id.cadReceita_valor)
        voltar = findViewById(R.id.cadReceita_voltar)

        buttonCadastro.setOnClickListener(this)
        nomeCadastro.setOnClickListener(this)
        valorCadastro.setOnClickListener(this)
        voltar.setOnClickListener(this)

        cadReceita_spinner_dia.adapter = ArrayAdapter(
            this, R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.dia)
        )

        cadReceita_spinner_mes.adapter = ArrayAdapter(
            this, R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.mes)
        )

        cadReceita_spinner_ano.adapter = ArrayAdapter(
            this, R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.ano)
        )

        val adapter = SpinnerAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listaTiposReceitas
        )

        val spinner = findViewById<Spinner>(R.id.cadReceita_spinner)
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
                        if (category!!.tipo == "Receita") {

                            listaTiposReceitas.add(category!!)
                        }
                    }
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.cadReceita_button -> {
                teste()
            }
            R.id.cadReceita_voltar -> {
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
                val receita = Receita(
                    nome = nomeCadastro.text.toString(),
                    tipo = listaTiposReceitas[cadReceita_spinner.selectedItemId.toInt()].nome,
                    userId = mAuth.currentUser!!.uid,
                    data = cadReceita_spinner_dia.selectedItem.toString() + cadReceita_spinner_mes.selectedItem.toString() + cadReceita_spinner_ano.selectedItem.toString(),
                    valor = valorCadastro.text.toString().toDouble()
                )

                val receitaRef = mDataBase.getReference("receitas")
                val receitaId = receitaRef.push().key

                receitaRef.child(receitaId!!).setValue(receita).addOnSuccessListener {
                    val dialog = AlertDialog.Builder(this)
                        .setMessage("Receita cadastrada com sucesso!")
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
