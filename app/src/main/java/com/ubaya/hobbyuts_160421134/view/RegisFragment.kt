package com.ubaya.hobbyuts_160421134.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ubaya.hobbyuts_160421134.databinding.FragmentRegisBinding
import com.ubaya.hobbyuts_160421134.viewmodel.LoginViewModel

class RegisFragment : Fragment() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentRegisBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisBinding.inflate(inflater,container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.btnBuatAkun.setOnClickListener {
            try {
                val namaDepan = binding.txtnamaDepan.text.toString()
                val namaBelakang = binding.txtNamaBelakang.text.toString()
                val userName = binding.txtUssername.text.toString()
                val email = binding.txtEmail.text.toString()
                val password = binding.txtPassDaftar.text.toString()
                val confPassword = binding.txtPassConfirm.text.toString()
                if (namaDepan.isNotEmpty() && namaBelakang.isNotEmpty() && userName.isNotEmpty() && email.isNotEmpty()
                    && password.isNotEmpty() && confPassword.isNotEmpty()) {
                    if (password == confPassword) {
                        viewModel.regisUser(userName, password, namaDepan, namaBelakang, email)
                    } else {
                        throw Exception("Password dan konfirmasi password tidak sama!")
                    }
                    viewModel.loginResultLD.observe(viewLifecycleOwner, Observer { userList ->
                        if (userList != null && userList.isNotEmpty()) {
                            val action = LoginFragmentDirections.actionHobbyListFragment()
                            Navigation.findNavController(requireView()).navigate(action)
                        }
                    })
                } else {
                    Toast.makeText(requireContext(), "tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "TERJADI ERROR", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnKembali.setOnClickListener {
            val action = RegisFragmentDirections.actionLoginFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }

    }

}