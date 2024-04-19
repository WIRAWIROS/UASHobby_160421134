package com.ubaya.hobbyuts_160421134.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.ubaya.hobbyuts_160421134.model.Hobby
import org.json.JSONObject

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val hobbyLD = MutableLiveData<Hobby>()
    val hobbyLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var queue: RequestQueue? = null

    val TAG = "volleyTag"

    fun fetch(hobbyId: String) {
        hobbyLoadErrorLD.value = false
        loadingLD.value = true

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://192.168.1.9/hobby/hobby.json"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONObject> { response ->
                try {
                    val jsonArray = response.getJSONArray("hobbies")
                    val gson = Gson()
                    for (i in 0 until jsonArray.length()) {
                        val hobby = gson.fromJson(jsonArray.getJSONObject(i).toString(), Hobby::class.java)
                        if (hobby.id == hobbyId) {
                            hobbyLD.value = hobby
                            loadingLD.value = false
                            return@Listener
                        }
                    }
                    hobbyLoadErrorLD.value = true
                    loadingLD.value = false
                } catch (e: Exception) {
                    Log.e(TAG, "Error parsing JSON: ${e.localizedMessage}")
                    hobbyLoadErrorLD.value = true
                    loadingLD.value = false
                }
            },
            Response.ErrorListener { error ->
                Log.e(TAG, "Volley error: $error")
                hobbyLoadErrorLD.value = true
                loadingLD.value = false
            })

        jsonObjectRequest.tag = TAG
        queue?.add(jsonObjectRequest)
    }


    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}