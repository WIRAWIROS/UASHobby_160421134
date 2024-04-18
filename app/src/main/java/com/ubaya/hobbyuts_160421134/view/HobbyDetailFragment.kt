package com.ubaya.hobbyuts_160421134.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.ubaya.hobbyuts_160421134.R
import com.ubaya.hobbyuts_160421134.databinding.FragmentHobbyDetailBinding
import com.ubaya.hobbyuts_160421134.viewmodel.DetailViewModel
import java.util.concurrent.TimeUnit

class HobbyDetailFragment : Fragment() {
    private lateinit var binding: FragmentHobbyDetailBinding
    private lateinit var viewModel: DetailViewModel
    private val args: HobbyDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHobbyDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        val hobbyId = args.hobbyId
        viewModel.fetch(hobbyId)

        viewModel.hobbyLD.observe(viewLifecycleOwner, Observer { hobby ->
            binding.txtJudul.setText(hobby.id)
            binding.txtNama.setText(hobby.nama)
            binding.txtIsi.setText(hobby.isi)

            Picasso.get()
                .load(hobby.photoUrl)
                .into(binding.imageView3, object : Callback {
                    override fun onSuccess() {
                        Log.d("Picasso Succes", "Succes")
                    }

                    override fun onError(e: Exception?) {
                        Log.e("Picasso Error", "Error loading image: ${e?.localizedMessage}")
                    }
                })
        })


        binding.btnBck.setOnClickListener {

        }
        binding.btnNext.setOnClickListener {

        }
    }

}