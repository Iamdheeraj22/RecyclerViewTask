package com.quad.recyclerviewtask

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quad.recyclerviewtask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() , RightShiftListItemClick,LeftShiftListItemClick {
    private lateinit var binding: ActivityMainBinding
    private var itemModelList1: ArrayList<ItemModel> = ArrayList()
    private var itemModelList2: ArrayList<ItemModel> = ArrayList()

    private lateinit var adapterLeftItem: LeftItemAdapter

    private lateinit var adapterRightItem: RightItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Set up the adapter
        val layoutManager1 :RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        val layoutManager2 :RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        binding.list1.layoutManager=layoutManager1
        binding.list2.layoutManager=layoutManager2

        //Left Item list
        setDummyInLeft()
        check()

        adapterLeftItem= LeftItemAdapter(this,itemModelList1,this)
        adapterRightItem=RightItemAdapter(this,itemModelList2,this)
        binding.list1.adapter=adapterLeftItem
        binding.list2.adapter=adapterRightItem

        binding.swiftRight.setOnClickListener {
            checkLeft()
            checkRight()
            adapterLeftItem.setList(itemModelList1)
            adapterRightItem.setList(itemModelList2)

        }

        binding.swiftLeft.setOnClickListener {
            checkRight()
            checkLeft()
            adapterRightItem.setList(itemModelList2)
            adapterLeftItem.setList(itemModelList1)
        }

        binding.addItem.setOnClickListener{
            addItem()
        }
    }

    private fun setDummyInLeft() {
        itemModelList1.add(ItemModel("LeftItem1"))
        itemModelList1.add(ItemModel("LeftItem2"))
        itemModelList1.add(ItemModel("LeftItem3"))
        itemModelList1.add(ItemModel("LeftItem4"))
        itemModelList1.add(ItemModel("LeftItem5"))
    }

    @Override
    override fun onRightShiftInsertClick(name: String) {
        super.onRightShiftInsertClick(name)
        itemModelList1.remove(ItemModel(name))
        itemModelList2.add(ItemModel(name))
        Log.d("listinformation","LeftList :-" + itemModelList1.toString()+"\n"+"Right list:-"+itemModelList2.toString())
    }

    @Override
    override fun onRightShiftDeleteClick(name: String) {
        super.onLeftShiftDeleteClick(name)
        itemModelList1.add(ItemModel(name))
        itemModelList2.remove(ItemModel(name))
        Log.d("listinformation","LeftList :-" + itemModelList1.toString()+"\n"+"Right list:-"+itemModelList2.toString())
    }

    @Override
    override fun onLeftShiftInsertClick(name: String) {
        super.onLeftShiftInsertClick(name)
        itemModelList2.remove(ItemModel(name))
        itemModelList1.add(ItemModel(name))
    }

    @Override
    override fun onLeftShiftDeleteClick(name: String) {
        super.onLeftShiftDeleteClick(name)
        itemModelList1.remove(ItemModel(name))
        itemModelList2.add(ItemModel(name))
    }

    fun checkLeft(){
       if(itemModelList2.isEmpty()){
           binding.swiftLeft.visibility=View.GONE
       }else{
           binding.swiftLeft.visibility=View.VISIBLE
       }
    }

    private fun checkRight() {
        if(itemModelList1.isEmpty()){
            binding.swiftRight.visibility=View.GONE
        }else{
            binding.swiftRight.visibility=View.VISIBLE
        }
    }


    fun check(){
        if(itemModelList2.isEmpty()){
            binding.swiftLeft.visibility=View.GONE
        }else if(itemModelList1.isEmpty()){
            binding.swiftRight.visibility=View.GONE
        }else if(itemModelList2.isNotEmpty()){
            binding.swiftLeft.visibility=View.VISIBLE
        }else if(itemModelList1.isNotEmpty()){
            binding.swiftRight.visibility=View.VISIBLE
        }
    }

    @SuppressLint("InflateParams")
    fun addItem()
    {
        try {
            val dialog = Dialog(this@MainActivity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.add_item_layout)
            dialog.show()
            val yesButton=dialog.findViewById<Button>(R.id.add_item_btn)
            val editext=dialog.findViewById<EditText>(R.id.itemEditText)
            val list1=dialog.findViewById<RadioButton>(R.id.listRadio1)
            val list2=dialog.findViewById<RadioButton>(R.id.listRadio2)

            yesButton.setOnClickListener {
                val name=editext.text.toString().trim()
                if(name.isEmpty()){
                    Toast.makeText(applicationContext,"Type the name",Toast.LENGTH_SHORT).show()
                }else if(!list1.isChecked && !list2.isChecked){
                    Toast.makeText(applicationContext,"Select the list which you want to add item...",Toast.LENGTH_SHORT).show()
                }else{
                    if(list1.isChecked){
                        itemModelList1.add(ItemModel(name))
                        adapterLeftItem.setList(itemModelList1)
                        check()
                        dialog.dismiss()
                    }else if(list2.isChecked){
                        itemModelList2.add(ItemModel(name))
                        adapterRightItem.setList(itemModelList2)
                        check()
                        dialog.dismiss()
                    }
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}