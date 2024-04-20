package com.ubaya.hobbyuts_160421134.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubaya.hobbyuts_160421134.model.User

class LoginViewModel (application: Application): AndroidViewModel(application) {
    val loginResultLD = MutableLiveData<ArrayList<User>>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null
    fun loginUser(username: String, password: String) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/utsanmp/signin.php?username=" + username + "&password=" + password
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<List<User>>() { }.type
                val result = Gson().fromJson<List<User>>(it, sType)
                loginResultLD.value = result as ArrayList<User>?

            },
            {
                Log.d("show_volley", it.toString())

            })
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun regisUser(namaDepan: String, namaBelakang: String, username: String, email: String, password: String) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/utsanmp/daftar.php?username=" + username + "&password=" + password+ "&namaDepan=" + namaDepan+ "&namaBelakang=" + namaBelakang+ "&email=" + email
        val stringRequest = StringRequest(
            Request.Method.POST, url,
            {
                val sType = object : TypeToken<List<User>>() { }.type
                val result = Gson().fromJson<List<User>>(it, sType)
                loginResultLD.value = result as ArrayList<User>?
            },
            {
                Log.d("show_volley", it.toString())

            })
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}