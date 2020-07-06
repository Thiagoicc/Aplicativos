package br.unifor.cct.bluepocket.activity.Login

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import br.unifor.cct.bluepocket.R
import br.unifor.cct.bluepocket.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var loginEmail: EditText
    private lateinit var loginSenha: EditText
    private lateinit var loginCriarConta: TextView
    private lateinit var loginBotao: Button
    private lateinit var loginBotaoTrocadeSenha: TextView

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginEmail = findViewById(R.id.login_text_email)
        loginSenha = findViewById(R.id.login_text_senha)
        loginCriarConta = findViewById(R.id.login_textView_criarConta)
        loginBotao = findViewById(R.id.login_button_entrar)
        loginBotaoTrocadeSenha = findViewById(R.id.login_button_trocasenha)

        loginCriarConta.setOnClickListener(this)
        loginBotao.setOnClickListener(this)
        loginBotaoTrocadeSenha.setOnClickListener(this)

    }

    override fun onStart() {
        super.onStart()
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login_textView_criarConta -> {
                val it = Intent(this, RegisterActivity::class.java)
                startActivity(it)

                loginEmail.text = null
                loginSenha.text = null
            }

            R.id.login_button_entrar -> {
                if (loginEmail.text.isEmpty() || loginSenha.text.isEmpty()) {
                    if (loginEmail.text.isEmpty()) {
                        loginEmail.error = "insira um email válido"
                    }
                    if (loginSenha.text.isEmpty()) {
                        loginSenha.error = "insira uma senha válida"
                    }
                } else {
                    val email = loginEmail.text.toString()
                    val senha = loginSenha.text.toString()


                    mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener() {
                        if (it.isSuccessful) {

                            loginEmail.text = null
                            loginSenha.text = null


                            val it = Intent(this, MainActivity::class.java)
                            startActivity(it)

                        } else {

                            val dialog = AlertDialog.Builder(this@LoginActivity)
                                .setMessage("email ou senha invalidos!")
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
            }

            R.id.login_button_trocasenha -> {
                val it = Intent(this, TrocadeSenha::class.java)
                startActivity(it)
            }
        }
    }
}


