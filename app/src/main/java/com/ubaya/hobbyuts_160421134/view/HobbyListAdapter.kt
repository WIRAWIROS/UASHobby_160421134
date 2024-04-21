package com.ubaya.hobbyuts_160421134.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ubaya.hobbyuts_160421134.databinding.HobbyListItemBinding
import com.ubaya.hobbyuts_160421134.model.Hobby
import com.squareup.picasso.Callback

class HobbyListAdapter(val hobbyList:ArrayList<Hobby>)
    : RecyclerView.Adapter<HobbyListAdapter.HobbyViewHolder>()
{
    class HobbyViewHolder(var binding: HobbyListItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HobbyViewHolder {
        val binding = HobbyListItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return HobbyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return hobbyList.size
    }

    override fun onBindViewHolder(holder: HobbyViewHolder, position: Int) {
        holder.binding.txtJudul.text = hobbyList[position].judul
        holder.binding.txtNama.text = "@"+hobbyList[position].nama
        holder.binding.txtIsi.text = hobbyList[position].desc

        holder.binding.btnDetail.setOnClickListener {
            val action = HobbyListFragmentDirections.actionHobbyDetailFragment(
                hobbyList[position].nama ?: "",
                hobbyList[position].judul ?: "",
                hobbyList[position].paragraf ?: "",
                hobbyList[position].photoUrl ?: "")
            holder.itemView.findNavController().navigate(action)
        }
        val picasso = Picasso.Builder(holder.itemView.context)
        picasso.listener { picasso, uri, exception ->
            exception.printStackTrace()
        }
        picasso.build().load(hobbyList[position].photoUrl)
            .into(holder.binding.imageView3, object: Callback {
                override fun onSuccess() {
                    holder.binding.progressBar2.visibility = View.INVISIBLE
                    holder.binding.imageView3.visibility = View.VISIBLE
                }
                override fun onError(e: Exception?) {
                    Log.e("picasso_error", e.toString())
                }
            })
    }
    fun updateHobbyList(newHobbyList: ArrayList<Hobby>) {
        hobbyList.clear()
        hobbyList.addAll(newHobbyList)
        notifyDataSetChanged()
    }


}