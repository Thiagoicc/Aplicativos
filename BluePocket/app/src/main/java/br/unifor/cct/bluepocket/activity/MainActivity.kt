package br.unifor.cct.bluepocket.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.unifor.cct.bluepocket.R
import br.unifor.cct.bluepocket.activity.Despesa.DespesaCadastro
import br.unifor.cct.bluepocket.activity.Despesa.ListarDespesa
import br.unifor.cct.bluepocket.activity.Login.LoginActivity
import br.unifor.cct.bluepocket.activity.Mensal.DemonstrativoMensal
import br.unifor.cct.bluepocket.activity.Receita.ListarReceita
import br.unifor.cct.bluepocket.activity.Receita.ReceitaCadastro
import br.unifor.cct.bluepocket.activity.Tipo.TipoCadastro
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var cadReceitaImageButton: ImageButton
    private lateinit var cadTipoImageButton: ImageButton
    private lateinit var cadDespesaCadastro: ImageButton
    private lateinit var lisDespesa: ImageButton
    private lateinit var lisReceita: ImageButton
    private lateinit var demonMensal: ImageButton
    private lateinit var voltar: ImageButton
    private lateinit var sair: TextView

    private lateinit var usuarioText: TextView

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        usuarioText = findViewById(R.id.Main_textView_usuario)

        cadReceitaImageButton = findViewById(R.id.main_imageButton_cadReceita)
        cadTipoImageButton = findViewById(R.id.main_imageButton_cadTipo)
        cadDespesaCadastro = findViewById(R.id.main_imageButton_cadDespesa)
        lisDespesa = findViewById(R.id.main_imageButton_lisDespesas)
        lisReceita = findViewById(R.id.main_imageButton_lisReceita)
        demonMensal = findViewById(R.id.main_imageButton_demoMensal)
        voltar = findViewById(R.id.main_imageButton_voltar)
        sair = findViewById(R.id.main_textView_clickable_sair)

        cadReceitaImageButton.setOnClickListener(this)
        cadTipoImageButton.setOnClickListener(this)
        cadDespesaCadastro.setOnClickListener(this)
        lisDespesa.setOnClickListener(this)
        lisReceita.setOnClickListener(this)
        demonMensal.setOnClickListener(this)
        voltar.setOnClickListener(this)
        sair.setOnClickListener(this)

        usuarioText.text = mAuth.currentUser!!.email
    }

    override fun onClick(v: View?) {

            when (v?.id) {
                R.id.main_imageButton_cadReceita -> {
                    val it = Intent(this, ReceitaCadastro::class.java)
                    startActivity(it)
                }

                R.id.main_imageButton_cadTipo -> {
                    val it = Intent(this, TipoCadastro::class.java)
                    startActivity(it)
                }

                R.id.main_imageButton_cadDespesa -> {
                    val it = Intent(this, DespesaCadastro::class.java)
                    startActivity(it)
                }

                R.id.main_imageButton_lisReceita -> {
                    val it = Intent(this, ListarReceita::class.java)
                    startActivity(it)
                }

                R.id.main_imageButton_lisDespesas-> {
                    val it = Intent(this, ListarDespesa::class.java)
                    startActivity(it)
                }

                R.id.main_imageButton_demoMensal-> {
                    val it = Intent(this, DemonstrativoMensal::class.java)
                    startActivity(it)
                }

                R.id.main_imageButton_voltar-> {
                    val it = Intent(this, LoginActivity::class.java)
                    startActivity(it)
                }
                R.id.main_textView_clickable_sair-> {
                    finishAffinity()
                }
            }
        }
    }

