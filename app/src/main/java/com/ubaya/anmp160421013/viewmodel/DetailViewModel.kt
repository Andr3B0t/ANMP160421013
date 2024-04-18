package com.ubaya.anmp160421013.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubaya.anmp160421013.model.Berita
import com.ubaya.anmp160421013.model.Detail

class DetailViewModel(application: Application): AndroidViewModel(application) {
    val detailLD = MutableLiveData<ArrayList<Detail>>()
    val detailLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    val TAG = "volleyTag"
    private var queue:RequestQueue? = null

    fun fetch(id:String) {
        loadingLD.value = true
        detailLoadErrorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://uasfspjav.000webhostapp.com/request_detail.php?berita_id=$id"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<List<Detail>>() { }.type
                val result = Gson().fromJson<List<Detail>>(it, sType)
                detailLD.value = result as ArrayList<Detail>?
                loadingLD.value = false
                Log.d("showvoley", it)
            },
            {
                Log.d("showvoley", it.toString())
                detailLoadErrorLD.value = false
                loadingLD.value = false
            })


        stringRequest.tag = TAG
        queue?.add(stringRequest)

    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }

}