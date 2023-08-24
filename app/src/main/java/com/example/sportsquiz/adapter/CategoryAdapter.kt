package com.example.sportsquiz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsquiz.databinding.CategoryitemBinding
import com.example.sportsquiz.model.CategoryModelClass

class CategoryAdapter(var categoryList:ArrayList<CategoryModelClass>):RecyclerView.Adapter<CategoryAdapter.MycategoryViewHolder>() {
    class MycategoryViewHolder(var binding: CategoryitemBinding):RecyclerView.ViewHolder(binding.root) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MycategoryViewHolder {
        return MycategoryViewHolder(CategoryitemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount()=categoryList.size

    override fun onBindViewHolder(holder: MycategoryViewHolder, position: Int) {
        var dataList=categoryList[position]
        holder.binding.categoryimage.setImageResource(dataList.catImage)
        holder.binding.category.text=dataList.catText
    }
}