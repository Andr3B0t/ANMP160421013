package com.ubaya.anmp160421013.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ubaya.anmp160421013.R
import com.ubaya.anmp160421013.databinding.FragmentLoginBinding
import com.ubaya.anmp160421013.databinding.FragmentProfileBinding
import java.time.LocalDateTime

class ProfileFragment : Fragment() {
    private lateinit var binding:FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var sharedFile = "com.ubaya.anmp160421013"
        var shared: SharedPreferences = this.requireContext().getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = shared.edit()
        binding.txtUsername.setText(shared.getString("username",""))
        binding.txtNamaDepan.setText(shared.getString("nama_depan",""))
        binding.txtNamaBelakang.setText(shared.getString("nama_belakang",""))
        binding.btnChange.setOnClickListener {
            val q = Volley.newRequestQueue(this.requireContext())
            val url_target = "https://uasfspjav.000webhostapp.com/ganti_password.php"
            val stringRequest = object: StringRequest(
                Request.Method.POST, url_target,
                {
                    Log.d("apiresult", it)
                    Toast.makeText(this.requireContext(), "Change Successful", Toast.LENGTH_SHORT).show()

                    //RESET
                    var editor: SharedPreferences.Editor=shared.edit()
                    editor.putString("nama_depan", binding.txtNamaDepan.text.toString())
                    editor.putString("nama_belakang", binding.txtNamaBelakang.text.toString())
                    editor.apply()

                },
                {
                    Log.e("apierror", it.printStackTrace().toString())
                }
            ) {
                override fun getParams(): MutableMap<String, String>? {
                    val params = HashMap<String, String>()
                    params["username"] = binding.txtUsername.text.toString()
                    params["nama_depan"] = binding.txtNamaDepan.text.toString()
                    params["nama_belakang"] = binding.txtNamaBelakang.text.toString()
                    params["password"] = binding.txtPassword.text.toString()
                    return params
                }
            }
            q.add(stringRequest)
        }
        binding.btnLogout.setOnClickListener {
            var sharedFile = "com.ubaya.anmp160421013"
            var shared: SharedPreferences = this.requireContext().getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
            var editor: SharedPreferences.Editor = shared.edit()
            editor.putString("username", "")
            editor.putString("nama_depan", "")
            editor.putString("nama_belakang", "")
            val intent = Intent(this.requireContext(), LoginActivity::class.java)
            editor.apply()
            startActivity(intent)
            this.requireActivity().finish()
        }
    }

}