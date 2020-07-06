package br.unifor.cct.bluepocket.activity.Tipo

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.unifor.cct.bluepocket.R
import br.unifor.cct.bluepocket.activity.MainActivity
import br.unifor.cct.bluepocket.entity.Tipo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_tipo_cadastro.*

class TipoCadastro : AppCompatActivity(), View.OnClickListener {

    private lateinit var buttonCadastro: Button
    private lateinit var nomeCadastro: TextView
    private lateinit var voltar: ImageButton

    private lateinit var mAuth: FirebaseAuth

    private lateinit var mDataBase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tipo_cadastro)


        mDataBase = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance()


        tipoCadastro_spinner_escolha.adapter = ArrayAdapter(
            this, R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.tipos)
        )

        buttonCadastro = findViewById(R.id.tipoCadastro_button_confirmar)
        nomeCadastro = findViewById(R.id.tipoCadastro_plainText_nome)
        voltar = findViewById(R.id.tipoCadastro_imageButton_voltar)

        buttonCadastro.setOnClickListener(this)
        nomeCadastro.setOnClickListener(this)
        voltar.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tipoCadastro_button_confirmar -> {
                val item = tipoCadastro_spinner_escolha.selectedItem
                val pos = tipoCadastro_spinner_escolha.selectedItemPosition

                val nome = tipoCadastro_plainText_nome.text.toString()

                if (tipoCadastro_plainText_nome.text.isEmpty()) {
                    tipoCadastro_plainText_nome.error = "Digite nome válido"
                } else {
                    val tipo = Tipo(
                        nome = nome,
                        tipo = item.toString(),
                        userId = mAuth.currentUser!!.uid
                    )

                    val tipoRef = mDataBase.getReference("tipos")
                    val tipoId = tipoRef.push().key

                    tipoRef.child(tipoId!!).setValue(tipo).addOnSuccessListener {
                        val dialog = AlertDialog.Builder(this)
                            .setMessage("Tipo cadastrado com sucesso!")
                            .setTitle("BluePocket")
                            .setCancelable(false)
                            .setNeutralButton(
                                "OK",
                                DialogInterface.OnClickListener { dialog, id ->
                                    dialog.dismiss()
                                }).create()
                        dialog.show()
                        nomeCadastro.text = null
                    }
                }
            }

            R.id.tipoCadastro_imageButton_voltar -> {
                val it = Intent(this, MainActivity::class.java)
                startActivity(it)
            }
        }
    }
}
