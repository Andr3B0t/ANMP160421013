package com.ubaya.anmp160421013.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ubaya.anmp160421013.R
import com.ubaya.anmp160421013.databinding.ActivityRegisterPageBinding

class RegisterPage : AppCompatActivity() {
    private lateinit var binding:ActivityRegisterPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegisterAcc.setOnClickListener(){
            var username:String=binding.txtUsernameReg.text.toString()
            var password:String=binding.txtPasswordReg.text.toString()
            var nama_depan:String=binding.txtNamaDepanReg.text.toString()
            var nama_belakang:String=binding.txtNamaBelakangReg.text.toString()
                val q = Volley.newRequestQueue(this)
                val url_target = "https://uasfspjav.000webhostapp.com/register.php"
                val stringRequest = object: StringRequest(
                    Request.Method.POST, url_target,
                    {
                        Log.d("apiresult", it)
                    },
                    {
                        Log.e("apierror", it.printStackTrace().toString())
                    }
                ) {
                    override fun getParams(): MutableMap<String, String>? {
                        val params = HashMap<String, String>()
                        params["username"] = username
                        params["password"] = password
                        params["nama_depan"] = nama_depan
                        params["nama_belakang"] = nama_belakang
                        return params
                    }
                }
                q.add(stringRequest)


                Toast.makeText(this, "Successfully Registered", Toast.LENGTH_SHORT).show()
                finish()
        }
    }
}