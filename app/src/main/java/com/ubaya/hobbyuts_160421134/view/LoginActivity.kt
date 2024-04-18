package com.ubaya.hobbyuts_160421134.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ubaya.hobbyuts_160421134.R
import com.ubaya.hobbyuts_160421134.databinding.ActivityLoginBinding
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnLogin.setOnClickListener {
            try {
                val username = binding.txtUsername.text.toString()
                val password = binding.txtPass.text.toString()

                if (username.isNotEmpty() && password.isNotEmpty()) {
                    loginUser(username, password)
                } else {
                    Toast.makeText(this, "Masukkan Username dan Password", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Terjadi kesalahan: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDaftar.setOnClickListener {
            val intent = Intent(this, RegisActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loginUser(ussername: String, password: String) {
        val url = "http://localhost/utsanmp/signin.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener<String> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    Log.d("LoginActivity", "Server response: $response")

                    val result = jsonObject.getString("result")
                    if (result == "success") {
                        // Login berhasil
                        Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()

                        // Simpan User ID yang login
                        val data = jsonObject.getJSONObject("data")
                        val idUser = data.getInt("id")

                        var sharedFile = "com.ubaya.hobbyuts_160421134"
                        var shared: SharedPreferences = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
                        var editor: SharedPreferences.Editor = shared.edit()
                        editor.putInt("ID", idUser)
                        editor.apply()

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Pesan kesalahan dari server
                        val message = jsonObject.optString("message", "Tidak ada pesan kesalahan")
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("LoginActivity", "Error parsing JSON: ${e.message}")
                    Toast.makeText(this, "Terjadi kesalahan: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener {
                Log.e("LoginActivity", "Volley error: $it")
                Toast.makeText(this, "Terjadi kesalahan jaringan", Toast.LENGTH_SHORT).show()
            }
        )
        {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["ussername"] = ussername
                params["password"] = password
                return params
            }
        }

        Volley.newRequestQueue(this).add(stringRequest)
    }
}