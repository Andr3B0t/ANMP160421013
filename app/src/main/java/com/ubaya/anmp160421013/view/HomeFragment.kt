package com.ubaya.anmp160421013.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ubaya.anmp160421013.R
import com.ubaya.anmp160421013.databinding.FragmentHomeBinding
import com.ubaya.anmp160421013.databinding.FragmentLoginBinding

class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var sharedFile = "com.ubaya.anmp160421013"
        var shared: SharedPreferences = this.requireContext().getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = shared.edit()
        var nama_depan = shared.getString("nama_depan","")
        var nama_belakang = shared.getString("nama_belakang","")
        binding.txtWelcome.setText("Welcome, " +nama_depan+" "+nama_belakang)
    }

}