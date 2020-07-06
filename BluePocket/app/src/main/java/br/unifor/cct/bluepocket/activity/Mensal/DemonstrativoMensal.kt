package br.unifor.cct.bluepocket.activity.Mensal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import br.unifor.cct.bluepocket.R
import br.unifor.cct.bluepocket.activity.MainActivity
import kotlinx.android.synthetic.main.activity_demonstrativo_mensal.*

class DemonstrativoMensal : AppCompatActivity(), View.OnClickListener {

    private lateinit var buttonDemon: Button
    private lateinit var voltar: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demonstrativo_mensal)

        demonMensal_spinner_mes.adapter = ArrayAdapter(
            this, R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.mes)
        )

        buttonDemon = findViewById(R.id.demonMensal_button_solicitar)
        voltar = findViewById(R.id.demonMesnsal_button_voltar)

        buttonDemon.setOnClickListener(this)
        voltar.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.demonMensal_button_solicitar -> {
                val it = Intent(this, DemonMensalView::class.java)
                it.putExtra("mes",demonMensal_spinner_mes.selectedItem.toString())
                startActivity(it)
            }

            R.id.demonMesnsal_button_voltar -> {
                val it = Intent(this, MainActivity::class.java)
                startActivity(it)
            }
        }
    }
}

