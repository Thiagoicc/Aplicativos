package br.unifor.cct.bluepocket.activity.Mensal

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.unifor.cct.bluepocket.R
import br.unifor.cct.bluepocket.activity.MainActivity
import br.unifor.cct.bluepocket.entity.Despesa
import br.unifor.cct.bluepocket.entity.Receita
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DemonMensalView : AppCompatActivity(), View.OnClickListener {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDataBase: FirebaseDatabase

    var listaDespesas = mutableListOf<Despesa>()
    var listaReceitas = mutableListOf<Receita>()

    private lateinit var voltar: ImageButton

    private lateinit var valortotalreceitas: TextView
    private lateinit var valortotalDespesas: TextView

    private lateinit var maiorReceita1:TextView
    private lateinit var maiorReceita2:TextView
    private lateinit var maiorReceita3:TextView
    private lateinit var maiorReceita4:TextView
    private lateinit var maiorReceita5:TextView

    private lateinit var maiorDespesa1:TextView
    private lateinit var maiorDespesa2:TextView
    private lateinit var maiorDespesa3:TextView
    private lateinit var maiorDespesa4:TextView
    private lateinit var maiorDespesa5:TextView

    private lateinit var tipoDespesa1:TextView
    private lateinit var tipoDespesa2:TextView
    private lateinit var tipoDespesa3:TextView

    private lateinit var tipoReceita1:TextView
    private lateinit var tipoReceita2:TextView
    private lateinit var tipoReceita3:TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demon_mensal_view)

        voltar = findViewById(R.id.imageButton_voltar)

        valortotalDespesas = findViewById(R.id.demonMensal_valorTotalDespesa)
        valortotalreceitas = findViewById(R.id.demonMensal_valorTotalReceita)

        maiorReceita1 = findViewById(R.id.demonMensal_maiorReceita_1)
        maiorReceita2 = findViewById(R.id.demonMensal_maiorReceita_2)
        maiorReceita3 = findViewById(R.id.demonMensal_maiorReceita_3)
        maiorReceita4 = findViewById(R.id.demonMensal_maiorReceita_4)
        maiorReceita5 = findViewById(R.id.demonMensal_maiorReceita_5)

        maiorDespesa1 = findViewById(R.id.demonMensal_maiorDespesa_1)
        maiorDespesa2 = findViewById(R.id.demonMensal_maiorDespesa_2)
        maiorDespesa3 = findViewById(R.id.demonMensal_maiorDespesa_3)
        maiorDespesa4 = findViewById(R.id.demonMensal_maiorDespesa_4)
        maiorDespesa5 = findViewById(R.id.demonMensal_maiorDespesa_5)

        tipoReceita1 = findViewById(R.id.demonMensal_tipoReceita_1)
        tipoReceita2 = findViewById(R.id.demonMensal_tipoReceita_2)
        tipoReceita3 = findViewById(R.id.demonMensal_tipoReceita_3)

        tipoDespesa1 = findViewById(R.id.demonMensal_tipoDespesa_1)
        tipoDespesa2 = findViewById(R.id.demonMensal_tipoDespesa_2)
        tipoDespesa3 = findViewById(R.id.demonMensal_tipoDespesa_3)

        voltar.setOnClickListener(this)


        mDataBase = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance()

    }

    override fun onStart() {
        super.onStart()

        var maiorReceita:Receita
        var maiorDespesa:Despesa

        var auxTipoNumReceita:Int
        var auxTipoNomeReceita:String

        var auxTipoNumDespesa:Int
        var auxTipoNomeDespesa:String


        var tiposReceitaNum = mutableListOf<Int>()
        var tiposReceitaNome = mutableListOf<String>()

        var tiposDespesaNum = mutableListOf<Int>()
        var tiposDespesaNome = mutableListOf<String>()

        var mes = intent.getStringExtra("mes")

        var totalReceita = 0.0
        var totalDespesa = 0.0

        //RECEITAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
        val receitaRef = mDataBase.getReference("receitas")
        val queryRes = receitaRef.orderByChild("userId").equalTo(mAuth.currentUser!!.uid)
            .addValueEventListener(object :
                ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    p0.children.forEach {
                        val category = it.getValue(Receita::class.java)
                        if (category!!.data[2] + "" + category!!.data[3] == mes)
                            listaReceitas.add(category!!)

                            listaReceitas.forEach() {

                            totalReceita += it.valor
                        }

                        valortotalreceitas.text = totalReceita.toString()


                    }

                    for(i in 0.. listaReceitas.size - 1) {
                        if(tiposReceitaNome.contains(listaReceitas[i].tipo)){
                            tiposReceitaNum[tiposReceitaNome.indexOf(listaReceitas[i].tipo)]++
                        }else{
                            tiposReceitaNome.add(listaReceitas[i].tipo)
                            tiposReceitaNum.add(1)
                        }
                        }

                    for (i in 0.. tiposReceitaNum.size ) {
                        for (ii in 0.. tiposReceitaNum.size - 2) {
                            if (tiposReceitaNum[ii] < tiposReceitaNum[ii + 1]) {

                                auxTipoNumReceita =  tiposReceitaNum[ii]
                                tiposReceitaNum[ii] = tiposReceitaNum[ii + 1]
                                tiposReceitaNum[ii + 1] = auxTipoNumReceita

                                auxTipoNomeReceita =  tiposReceitaNome[ii]
                                tiposReceitaNome[ii] = tiposReceitaNome[ii + 1]
                                tiposReceitaNome[ii + 1] = auxTipoNomeReceita
                            }

                        }
                    }

                    when(tiposReceitaNome.size){
                        1 -> {
                            tipoReceita1.text = tiposReceitaNome[0]
                        }

                        2 -> {
                            tipoReceita1.text = tiposReceitaNome[0]
                            tipoReceita2.text = tiposReceitaNome[1]
                        }

                        3 -> {
                            tipoReceita1.text = tiposReceitaNome[0]
                            tipoReceita2.text = tiposReceitaNome[1]
                            tipoReceita3.text = tiposReceitaNome[2]
                        }

                    }


                    for (i in 0.. listaReceitas.size + 1) {
                        for (ii in 0.. listaReceitas.size - 2) {
                            if (listaReceitas[ii].valor < listaReceitas[ii + 1].valor) {

                                maiorReceita = listaReceitas[ii]
                                listaReceitas[ii] = listaReceitas[ii + 1]
                                listaReceitas[ii + 1] =  maiorReceita
                            }

                        }
                    }

                    when(listaReceitas.size){
                        1 -> {
                            maiorReceita1.text = listaReceitas[0].nome
                        }

                        2 -> {
                            maiorReceita1.text = listaReceitas[0].nome
                            maiorReceita2.text = listaReceitas[1].nome
                        }

                        3 -> {
                            maiorReceita1.text = listaReceitas[0].nome
                            maiorReceita2.text = listaReceitas[1].nome
                            maiorReceita3.text = listaReceitas[2].nome
                        }

                        4 -> {
                            maiorReceita1.text = listaReceitas[0].nome
                            maiorReceita2.text = listaReceitas[1].nome
                            maiorReceita3.text = listaReceitas[2].nome
                            maiorReceita4.text = listaReceitas[3].nome
                        }

                        5 -> {
                            maiorReceita1.text = listaReceitas[0].nome
                            maiorReceita2.text = listaReceitas[1].nome
                            maiorReceita3.text = listaReceitas[2].nome
                            maiorReceita4.text = listaReceitas[3].nome
                            maiorReceita5.text = listaReceitas[4].nome
                        }
                    }

                }
            })

        //DESPESAAAAAAAAAAAAAAAAAAAAAAAAA

        val despesaRef = mDataBase.getReference("despesas")
        val queryDes = despesaRef.orderByChild("userId").equalTo(mAuth.currentUser!!.uid)
            .addValueEventListener(object :
                ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    p0.children.forEach {
                        val category = it.getValue(Despesa::class.java)
                        if (category!!.data[2] + "" + category!!.data[3] == mes)
                            listaDespesas.add(category!!)

                        listaDespesas.forEach() {

                            totalDespesa += it.valor
                        }

                        valortotalDespesas.text = totalDespesa.toString()


                    }

                    for(i in 0.. listaDespesas.size - 1) {
                        if(tiposDespesaNome.contains(listaDespesas[i].tipo)){
                            tiposDespesaNum[tiposDespesaNome.indexOf(listaDespesas[i].tipo)]++
                        }else{
                            tiposDespesaNome.add(listaDespesas[i].tipo)
                            tiposDespesaNum.add(1)
                        }
                    }

                    for (i in 0.. tiposDespesaNum.size + 10000 ) {
                        for (ii in 0.. tiposDespesaNum.size - 1 - 1) {
                            if (tiposDespesaNum[ii] < tiposDespesaNum[ii + 1]) {

                                auxTipoNumDespesa =  tiposDespesaNum[ii]
                                tiposDespesaNum[ii] = tiposDespesaNum[ii + 1]
                                tiposDespesaNum[ii + 1] = auxTipoNumDespesa

                                auxTipoNomeDespesa =  tiposDespesaNome[ii]
                                tiposDespesaNome[ii] = tiposDespesaNome[ii + 1]
                                tiposDespesaNome[ii + 1] = auxTipoNomeDespesa
                            }

                        }
                    }

                    when(tiposDespesaNome.size){
                        1 -> {
                            tipoDespesa1.text = tiposDespesaNome[0]
                        }

                        2 -> {
                            tipoDespesa1.text = tiposDespesaNome[0]
                            tipoDespesa2.text = tiposDespesaNome[1]
                        }

                        3 -> {
                            tipoDespesa1.text = tiposDespesaNome[0]
                            tipoDespesa2.text = tiposDespesaNome[1]
                            tipoDespesa3.text = tiposDespesaNome[2]
                        }

                    }


                    for (i in 0.. listaDespesas.size ) {
                        for (ii in 0.. listaDespesas.size - 2 ) {
                            if (listaDespesas[ii].valor < listaDespesas[ii + 1].valor) {

                                maiorDespesa = listaDespesas[ii]
                                listaDespesas[ii] = listaDespesas[ii + 1]
                                listaDespesas[ii + 1] =  maiorDespesa
                            }

                        }
                    }

                    when(listaDespesas.size){
                        1 -> {
                            maiorDespesa1.text = listaDespesas[0].nome
                        }

                        2 -> {
                            maiorDespesa1.text = listaDespesas[0].nome
                            maiorDespesa2.text = listaDespesas[1].nome
                        }

                        3 -> {
                            maiorDespesa1.text = listaDespesas[0].nome
                            maiorDespesa2.text = listaDespesas[1].nome
                            maiorDespesa3.text = listaDespesas[2].nome
                        }

                        4 -> {
                            maiorDespesa1.text = listaDespesas[0].nome
                            maiorDespesa2.text = listaDespesas[1].nome
                            maiorDespesa3.text = listaDespesas[2].nome
                            maiorDespesa4.text = listaDespesas[3].nome
                        }

                        5 -> {
                            maiorDespesa1.text = listaDespesas[0].nome
                            maiorDespesa2.text = listaDespesas[1].nome
                            maiorDespesa3.text = listaDespesas[2].nome
                            maiorDespesa4.text = listaDespesas[3].nome
                            maiorDespesa5.text = listaDespesas[4].nome
                        }
                    }

                }
            })



    }

    fun valorTotalReceita() {
        var mes = intent.getStringExtra("mes")

        var totalDespesa = 0.0

        val despesaoRef = mDataBase.getReference("despesas")
        val queryDes = despesaoRef.orderByChild("userId").equalTo(mAuth.currentUser!!.uid)
            .addValueEventListener(object :
                ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    p0.children.forEach {
                        val category = it.getValue(Despesa::class.java)
                        if (category!!.data[2] + "" + category!!.data[3] == mes)
                            listaDespesas.add(category!!)

                    }

                    listaDespesas.forEach() {
                        totalDespesa += it.valor
                    }

                    valortotalDespesas.text = totalDespesa.toString()
                    maioresReceitas()
                }
            })
    }

    fun valorTotalDepesa()  {
        var mes = intent.getStringExtra("mes")

        var totalReceita = 0.0

        val receitaRef = mDataBase.getReference("receitas")
        val queryRes = receitaRef.orderByChild("userId").equalTo(mAuth.currentUser!!.uid)
            .addValueEventListener(object :
                ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    p0.children.forEach {
                        val category = it.getValue(Receita::class.java)
                        if (category!!.data[2] + "" + category!!.data[3] == mes)
                            listaReceitas.add(category!!)

                        listaReceitas.forEach() {

                            totalReceita += it.valor
                        }

                        valortotalreceitas.text = totalReceita.toString()

                    }
                }
            })
    }

    @SuppressLint("SetTextI18n")
    fun maioresReceitas()   {
        var maiorDespesa:Despesa

        for (i in 0 until listaDespesas.size) {
            for (ii in 0 until listaDespesas.size) {
                if (listaDespesas[ii].valor > listaDespesas[ii + 1].valor) {
                    maiorDespesa = listaDespesas[ii]
                    listaDespesas[ii] = listaDespesas[ii + 1]
                    listaDespesas[ii + 1] = maiorDespesa
                }

            }
        }


    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.cadDespesa_voltar -> {
            val it = Intent(this, MainActivity::class.java)
            startActivity(it)
        }
        }
    }

}
