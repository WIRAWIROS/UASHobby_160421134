package com.ubaya.hobbyuts_160421134.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ubaya.hobbyuts_160421134.databinding.FragmentLoginBinding
import com.ubaya.hobbyuts_160421134.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.btnLogin.setOnClickListener {
            val username = binding.txtUsername.text.toString()
            val password = binding.txtPass.text.toString()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                viewModel.loginUser(username, password)
            } else {
                Toast.makeText(requireContext(), "Username dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.loginResultLD.observe(viewLifecycleOwner, Observer { user ->
            if (user != null && user.isNotEmpty()) {
                val action = LoginFragmentDirections.actionHobbyListFragment()
                findNavController().navigate(action)
                saveLoginDetails(id)
            } else {
                Toast.makeText(requireContext(), "Username atau Password salah", Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnDaftar.setOnClickListener {
            val action = LoginFragmentDirections.actionRegisFragment()
            findNavController().navigate(action)
        }
    }
    private fun saveLoginDetails(id: Int) {
        val sharedPref = activity?.getSharedPreferences("myPref", Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putInt("id", id)
            putBoolean("isLoggedIn", true)
            apply()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clear()
    }
}
