package br.unifor.cct.bluepocket.activity.Login

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import br.unifor.cct.bluepocket.R
import br.unifor.cct.bluepocket.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var registerEmail: EditText
    private lateinit var registerNome: EditText
    private lateinit var registerSenha: EditText
    private lateinit var registerConfirmarSenha: EditText
    private lateinit var registerRegistrar: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerEmail = findViewById(R.id.register_text_email)
        registerSenha = findViewById(R.id.register_text_senha)
        registerRegistrar = findViewById(R.id.register_button_registrar)
        registerConfirmarSenha = findViewById(R.id.register_text_confirmarSenha)
        registerNome = findViewById(R.id.register_text_nome)

        registerRegistrar.setOnClickListener() {
            if (registerNome.text.isEmpty()) {
                registerNome.error = "Insira Seu Nome"
            }

            if (registerSenha.text.isEmpty()) {
                registerSenha.error = "Insira a Senha"
            }

            if (registerEmail.text.isEmpty()) {
                registerEmail.error = "Insira Seu Email"
            }

            if (registerConfirmarSenha.text.isEmpty()) {
                registerConfirmarSenha.error = "Confirme a Senha"
            }

            if (!registerNome.text.isEmpty()
                && !registerSenha.text.isEmpty()
                && !registerEmail.text.isEmpty()
                && !registerConfirmarSenha.text.isEmpty()
            ) {

                if (registerSenha.text.toString()
                        .equals(registerConfirmarSenha.text.toString())
                ) {

                    val nome = registerNome.text.toString()
                    val email = registerEmail.text.toString()
                    val senha = registerSenha.text.toString()

                    mAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener() {
                        if (it.isSuccessful) {
                            Log.i("BluePocket", it.result.toString())

                            //TODO: Criar um objeto do tipo usuário
                            val user = User(nome, email)

                            //TODO: Armazenar esse objeto no banco de dados
                                val userRef = mDatabase.getReference("Users")
                            userRef.child(mAuth.currentUser!!.uid).setValue(user)

                            //TODO: Exibir caixa de diálogo informando o registro do usuário
                            val dialog = AlertDialog.Builder(this@RegisterActivity)
                                .setMessage("Usuário Cadastrado com sucesso!")
                                .setTitle("BluePocket")
                                .setCancelable(false)
                                .setNeutralButton(
                                    "OK",
                                    DialogInterface.OnClickListener { dialog, id ->
                                        dialog.dismiss()
                                        finish()
                                    }).create()

                            dialog.show()

                        } else {
                            Log.e("BluePocket", it.exception.toString())

                            val dialog = AlertDialog.Builder(this@RegisterActivity)
                                .setMessage(it.exception!!.message)
                                .setTitle("BluePocket")
                                .setCancelable(false)
                                .setNeutralButton(
                                    "OK",
                                    DialogInterface.OnClickListener { dialog, id ->
                                        dialog.dismiss()
                                        registerSenha.error = "Senha de no minimo 6 caracteres"
                                    }).create()

                            dialog.show()
                        }
                    }

                } else {
                    registerConfirmarSenha.error = "As senhas não Conferem"
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance()
    }
}

