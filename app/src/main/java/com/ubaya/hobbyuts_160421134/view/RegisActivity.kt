package com.ubaya.hobbyuts_160421134.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ubaya.hobbyuts_160421134.R
import com.ubaya.hobbyuts_160421134.databinding.ActivityRegisBinding
import org.json.JSONObject

class RegisActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnBuatAkun.setOnClickListener {
            try {
                val usserName = binding.txtUsernameDaftar.text.toString()
                val password = binding.txtPassDaftar.text.toString()
                val confPassword = binding.txtPassConfirm.text.toString()

                if (usserName.isNotEmpty() && password.isNotEmpty() && confPassword.isNotEmpty()) {
                    if (password == confPassword) {
                        registerUser(usserName, password)
                    } else {
                        throw Exception("Password dan konfirmasi password tidak sama!")
                    }
                } else {
                    throw Exception("Masukkan email dan password")
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Terjadi kesalahan: ${e.message}!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnKembali.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun registerUser(ussername: String, password: String) {
        Log.d("RegisActivity", "Ussername: $ussername, Password: $password")

        val url = "http://localhost/utsanmp/daftar.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener<String> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val result = jsonObject.getString("result")

                    if (result == "Success") {
                        // Registrasi berhasil
                        Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val message = jsonObject.getString("message")
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Terjadi kesalahan: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener {
                Toast.makeText(this, "Terjadi kesalahan jaringan", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["ussername"] = ussername
                params["password"] = password
                return params
            }
        }

        // Tambahkan request ke queue Volley
        Volley.newRequestQueue(this).add(stringRequest)
    }
}