package com.ubaya.hobbyuts_160421134.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.ubaya.hobbyuts_160421134.R
import com.ubaya.hobbyuts_160421134.databinding.FragmentHobbyDetailBinding

class HobbyDetailFragment : Fragment()  {
    private lateinit var  binding:FragmentHobbyDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHobbyDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val judul =
            HobbyDetailFragmentArgs.fromBundle(requireArguments()).judul
        val nama =
            HobbyDetailFragmentArgs.fromBundle(requireArguments()).nama
        val paragraf =
            HobbyDetailFragmentArgs.fromBundle(requireArguments()).paragraf
        val photoUrl =
            HobbyDetailFragmentArgs.fromBundle(requireArguments()).photo
        binding.txtJudul.text = judul
        binding.txtNama.text = "@"+nama
        binding.txtIsi.text = paragraf
        Picasso.get()
            .load(photoUrl)
            .into(binding.imageView3)
    }


}