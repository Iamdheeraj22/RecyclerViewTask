package com.quad.recyclerviewtask

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

class RightItemAdapter internal constructor(
    private val context: Context,
    private var rightArrayList: ArrayList<ItemModel>,
    private val onItemClick:LeftShiftListItemClick
) : RecyclerView.Adapter<RightItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: java.util.ArrayList<ItemModel>) {
        rightArrayList=list
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        var status=false
        holder.textView.text = rightArrayList[position].name
        holder.textView.setBackgroundResource(R.drawable.gray_background_5dp)

        holder.itemView.setOnClickListener {
            try {
                if(status){
                    status=false
                    holder.itemView.setBackgroundResource(R.drawable.gray_background_5dp)
                    onItemClick.onLeftShiftDeleteClick(holder.textView.text.toString())
                }else{
                    status=true
                    holder.itemView.setBackgroundResource(R.drawable.red_background_5dp)
                    onItemClick.onLeftShiftInsertClick(holder.textView.text.toString())
                }
            }catch (e:Exception){
                Toast.makeText(context,e.localizedMessage,Toast.LENGTH_SHORT).show();
            }
        }
    }

    override fun getItemCount(): Int {
        return rightArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.itemName)
    }
}

interface LeftShiftListItemClick{
    fun onLeftShiftInsertClick(name:String){}
    fun onLeftShiftDeleteClick(name:String){}
}