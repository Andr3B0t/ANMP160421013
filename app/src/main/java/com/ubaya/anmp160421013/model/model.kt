package com.ubaya.anmp160421013.model

data class User(
    val username:String,
    val nama_depan:String?,
    val nama_belakang:String?,
    val password:String?
)

data class Berita(
    val id:Int,
    val nama:String?,
    val url:String?,
    val writer_username:String?
)

data class Detail(
    val id: Int,
    val page:Int?,
    val isi:String?,
    val beritas_id:Int?
)
