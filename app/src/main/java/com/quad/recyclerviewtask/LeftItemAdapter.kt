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
import java.util.*
import kotlin.collections.ArrayList

class LeftItemAdapter internal constructor(
    private val context: Context,
    private var leftArrayList: ArrayList<ItemModel>,
    private val onItemClick:RightShiftListItemClick
) : RecyclerView.Adapter<LeftItemAdapter.ViewHolder>() {
    private var index = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list:ArrayList<ItemModel>){
        leftArrayList=list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        var status =false
        val info=leftArrayList[position]
        holder.textView.text = leftArrayList[position].name
        holder.textView.setBackgroundResource(R.drawable.gray_background_5dp)
        holder.itemView.setOnClickListener {
            try {
                if(status){
                    status=false
                    holder.itemView.setBackgroundResource(R.drawable.gray_background_5dp)
                    onItemClick.onRightShiftDeleteClick(holder.textView.text.toString())
                }else{
                    status=true
                    holder.itemView.setBackgroundResource(R.drawable.red_background_5dp)
                    onItemClick.onRightShiftInsertClick(holder.textView.text.toString())
                }
            }catch (e: Exception){
                Toast.makeText(context,e.localizedMessage, Toast.LENGTH_SHORT).show();
            }
        }
    }

    override fun getItemCount(): Int {
        return leftArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.itemName)
    }
}

interface RightShiftListItemClick{
    fun onRightShiftInsertClick(name:String){}
    fun onRightShiftDeleteClick(name:String){}
}