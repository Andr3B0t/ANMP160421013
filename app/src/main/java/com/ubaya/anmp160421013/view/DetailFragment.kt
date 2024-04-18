package com.ubaya.anmp160421013.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.ubaya.anmp160421013.R
import com.ubaya.anmp160421013.databinding.FragmentDetailBinding
import com.ubaya.anmp160421013.databinding.FragmentLoginBinding
import com.ubaya.anmp160421013.viewmodel.DetailViewModel
import java.util.concurrent.TimeUnit

class DetailFragment : Fragment() {
    private lateinit var binding:FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtTitle.text = DetailFragmentArgs.fromBundle(requireArguments()).title
        binding.txtAuthor.text = "@"+DetailFragmentArgs.fromBundle(requireArguments()).author
        binding.btnPrev.isEnabled = false
        binding.btnNext.isEnabled = false
        val picasso = Picasso.Builder(this.requireContext())
        picasso.listener { picasso, uri, exception ->
            exception.printStackTrace()
        }
        picasso.build().load(DetailFragmentArgs.fromBundle(requireArguments()).url).into(binding.imageView, object:
            Callback {
            override fun onSuccess() {
                binding.progressLoadImg2.visibility = View.INVISIBLE
                binding.imageView.visibility = View.VISIBLE
            }

            override fun onError(e: Exception?) {
                Log.e("picasso_error", e.toString())
            }

        })

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        if(arguments != null) {
            val ID =
                DetailFragmentArgs.fromBundle(requireArguments()).id
            viewModel.fetch(ID.toString())
            observeViewModel()
        }
    }

    fun observeViewModel() {
        viewModel.detailLD.observe(viewLifecycleOwner, Observer{
            var detail = it
            var page = 0
            binding.txtSubtitle.text = detail[page].subtitle
            binding.txtIsi.text = detail[page].isi

            if (detail.size>1){
                binding.btnNext.isEnabled=true
            }
            binding.btnPrev.setOnClickListener {
                binding.btnNext.isEnabled=true
                page--
                if (page==0){
                    binding.btnPrev.isEnabled=false
                }
                binding.txtSubtitle.text = detail[page].subtitle
                binding.txtIsi.text = detail[page].isi
            }
            binding.btnNext.setOnClickListener {
                binding.btnPrev.isEnabled=true
                page++
                if (page==detail.size-1){
                    binding.btnNext.isEnabled=false
                }
                binding.txtSubtitle.text = detail[page].subtitle
                binding.txtIsi.text = detail[page].isi
            }
        })

    }

}