package br.unifor.cct.bluepocket.entity

data class Receita (
    var nome:String = "" ,
    var data:String = "" ,
    var valor:Double = 0.0 ,
    var userId:String = "" ,
    var tipo:String = ""
)