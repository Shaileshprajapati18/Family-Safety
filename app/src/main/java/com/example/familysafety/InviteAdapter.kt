package com.example.familysafety

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.familysafety.Model.ContactModel

class InviteAdapter(private val listContact: List<ContactModel>) : RecyclerView.Adapter<InviteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InviteAdapter.ViewHolder {

        val inflater=LayoutInflater.from(parent.context)
        val item=inflater.inflate(R.layout.item_invite,parent,false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item= listContact[position]
        holder.name.text=item.name    }

    override fun getItemCount(): Int {
        return listContact.size
    }
    class ViewHolder(private val item: View): RecyclerView.ViewHolder(item) {

        val imageUser = item.findViewById<ImageView>(R.id.img_user)
        val name = item.findViewById<TextView>(R.id.name)
        //val number = item.findViewById<TextView>(R.id.n)



    }
}