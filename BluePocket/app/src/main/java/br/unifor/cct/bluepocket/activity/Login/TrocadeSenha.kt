package br.unifor.cct.bluepocket.activity.Login

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.unifor.cct.bluepocket.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class TrocadeSenha : AppCompatActivity(), View.OnClickListener {

    private lateinit var emailTextView: TextView
    private lateinit var confirmaButton: Button


    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trocade_senha)

        emailTextView = findViewById(R.id.trocaSenha_text_email)
        confirmaButton = findViewById(R.id.trocaSenha_button_redefinir)

        confirmaButton.setOnClickListener(this)



        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance()

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.trocaSenha_button_redefinir -> {
                val email = emailTextView.text.toString()

                mAuth.sendPasswordResetEmail(email).addOnCompleteListener() {
                    if(it.isSuccessful) {

                        /** val user = mDatabase.getReference("Users/"+mAuth.currentUser!!.uid).setValue(
                            senha = mAuth.currentUser!!.
                        )**/


                        emailTextView.text = null

                        val dialog = AlertDialog.Builder(this@TrocadeSenha)
                            .setMessage("Verifique seu email")
                            .setTitle("BluePocket")
                            .setCancelable(false)
                            .setNeutralButton(
                                "OK",
                                DialogInterface.OnClickListener { dialog, id ->
                                    dialog.dismiss()
                                }).create()
                        dialog.show()
                    } else  {
                        val dialog = AlertDialog.Builder(this@TrocadeSenha)
                            .setMessage("Email ou Senha Inválidos!")
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
    }
}
