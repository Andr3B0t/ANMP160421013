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
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubaya.anmp160421013.R
import com.ubaya.anmp160421013.databinding.ActivityLoginBinding
import com.ubaya.anmp160421013.databinding.FragmentLoginBinding
import com.ubaya.anmp160421013.model.User
import org.json.JSONObject

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var sharedFile = "com.ubaya.anmp160421013"
        var shared: SharedPreferences = this.requireContext().getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = shared.edit()

        editor.putString("username", "")
        editor.putString("nama_depan", "")
        editor.putString("nama_belakang", "")
        editor.apply()

        binding.btnRegister2.setOnClickListener {
//            val intent = Intent(this, RegisterPage::class.java)
//            startActivity(intent)
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.btnSignIn2.setOnClickListener {
            val q = Volley.newRequestQueue(this.requireContext())
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
                        val intent = Intent(this.requireContext(), HomePage::class.java)
                        editor.putString("username", user.username)
                        editor.putString("nama_depan", user.nama_depan)
                        editor.putString("nama_belakang", user.nama_belakang)
                        editor.apply()
                        startActivity(intent)
                        requireActivity().finish()
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