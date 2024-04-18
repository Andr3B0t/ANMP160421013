package com.ubaya.anmp160421013.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubaya.anmp160421013.model.Berita

class ListViewModel(application: Application): AndroidViewModel(application) {
    val newsLD = MutableLiveData<ArrayList<Berita>>()
    val newsLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue:RequestQueue? = null

    fun refresh() {
        loadingLD.value = true
        newsLoadErrorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://uasfspjav.000webhostapp.com/request_berita.php"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<List<Berita>>() { }.type
                val result = Gson().fromJson<List<Berita>>(it, sType)
                newsLD.value = result as ArrayList<Berita>?
                loadingLD.value = false
                Log.d("showvoley", it)
            },
            {
                Log.d("showvoley", it.toString())
                newsLoadErrorLD.value = false
                loadingLD.value = false
            })

        stringRequest.tag = TAG
        queue?.add(stringRequest)

//        newsLoadErrorLD.value = false
//        loadingLD.value = true



//        newsLD.value = arrayListOf(
//            Berita(1,"berita1","https://images.alphacoders.com/128/1289833.jpg","andre","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum fringilla tristique ipsum sed mollis. In convallis vel enim eget tempor. Aliquam a massa nibh. Donec eget eros vitae dui faucibus convallis in auctor ex. Quisque fringilla hendrerit sem non tempus. Vestibulum ante ipsum primis in faucibus orci luctus"),
//            Berita(2,"berita2","https://images.alphacoders.com/128/1289833.jpg","victor","test"),
//            Berita(3,"berita3","https://images.alphacoders.com/128/1289833.jpg","erico","test"),
//        )
//
//        newsLoadErrorLD.value = false
//        loadingLD.value = false
    }

}