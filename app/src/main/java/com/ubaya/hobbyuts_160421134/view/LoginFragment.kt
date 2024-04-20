package com.ubaya.hobbyuts_160421134.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ubaya.hobbyuts_160421134.databinding.FragmentLoginBinding
import com.ubaya.hobbyuts_160421134.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewmodel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.btnLogin.setOnClickListener {
            try {
                val username = binding.txtUsername.text.toString()
                val password = binding.txtPass.text.toString()
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    viewmodel.loginUser(username, password)
                    viewmodel.loginResultLD.observe(viewLifecycleOwner, Observer { userList ->
                        if (userList != null && userList.isNotEmpty()) {
                            val action = LoginFragmentDirections.actionHobbyListFragment()
                            Navigation.findNavController(requireView()).navigate(action)
                            
                        } else {
                            Toast.makeText(requireContext(), "Username atau Password salah", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    Toast.makeText(requireContext(), "Username dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "TERJADI ERROR", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnDaftar.setOnClickListener {
            val action = LoginFragmentDirections.actionRegisFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }
    }
}