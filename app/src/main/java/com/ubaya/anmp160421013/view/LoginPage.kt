package com.ubaya.anmp160421013.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubaya.anmp160421013.R
import com.ubaya.anmp160421013.databinding.ActivityLoginPageBinding
import com.ubaya.anmp160421013.model.User
import org.json.JSONObject

class LoginPage : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var sharedFile = "com.ubaya.anmp160421013"
        var shared: SharedPreferences = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = shared.edit()

        editor.putString("username", "")
        editor.putString("nama_depan", "")
        editor.putString("nama_belakang", "")
        editor.apply()

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }

        binding.btnSignIn.setOnClickListener {
            val q = Volley.newRequestQueue(this)
            val url = "https://uasfspjav.000webhostapp.com/login.php"
            val username = binding.txtUsername.text.toString()
            val password = binding.txtPassword.text.toString()
            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                {
                    Log.d("apiresult", it.toString())
                    val obj = JSONObject(it)
                    if (obj.getString("result") == "success") {
                        val data = obj.getJSONArray("data")
                        val sType = object : TypeToken<ArrayList<User>>() {}.type
                        val user = (Gson().fromJson(data.toString(), sType) as ArrayList<User>)[0]
                        Log.d("apiresult", user.toString())
                        val intent = Intent(this, HomePage::class.java)
                        editor.putString("username", user.username)
                        editor.putString("nama_depan", user.nama_depan)
                        editor.putString("nama_belakang", user.nama_belakang)
                        editor.apply()
                        startActivity(intent)
                        finish()
                    }
                },
                {
                    Log.e("apiresult", it.message.toString())
                }) {
                override fun getParams(): MutableMap<String, String>? {
                    val params = HashMap<String, String>()
                    params["username"] = username
                    params["password"] = password
                    return params
                }
            }
            q.add(stringRequest)
        }
    }
}