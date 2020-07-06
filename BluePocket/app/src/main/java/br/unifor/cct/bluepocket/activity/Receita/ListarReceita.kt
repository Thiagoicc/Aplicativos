package br.unifor.cct.bluepocket.activity.Receita

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.unifor.cct.bluepocket.R
import br.unifor.cct.bluepocket.entity.Receita
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListarReceita : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDataBase: FirebaseDatabase

    var listaReceitas = mutableListOf<Receita>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_receita)

        recyclerView = findViewById(R.id.ListarReceita_recyclerView)

        mDataBase = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance()

    }

    override fun onStart() {
        super.onStart()


        val despesaoRef = mDataBase.getReference("receitas")
        val query = despesaoRef.orderByChild("userId").equalTo(mAuth.currentUser!!.uid).addValueEventListener(object:
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach{
                    val category = it.getValue(Receita::class.java)

                    listaReceitas.add(category!!)

                    val layoutManager = LinearLayoutManager(applicationContext)
                    val adapter =
                        ListaAdapterReceita(
                            applicationContext,
                            listaReceitas
                        )


                    recyclerView.layoutManager = layoutManager
                    recyclerView.adapter = adapter


                }
            }

        })

    }
}
