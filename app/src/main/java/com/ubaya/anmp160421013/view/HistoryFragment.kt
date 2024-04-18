package com.ubaya.anmp160421013.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ubaya.anmp160421013.R
import com.ubaya.anmp160421013.databinding.FragmentDetailBinding
import com.ubaya.anmp160421013.databinding.FragmentHistoryBinding
import com.ubaya.anmp160421013.databinding.FragmentLoginBinding

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

}