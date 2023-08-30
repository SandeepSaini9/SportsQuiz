package com.example.sportsquiz.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsquiz.QuizActivity
import com.example.sportsquiz.databinding.CategoryitemBinding
import com.example.sportsquiz.model.CategoryModelClass

class CategoryAdapter(
    var categoryList: ArrayList<CategoryModelClass>, var requireActivity: FragmentActivity
) : RecyclerView.Adapter<CategoryAdapter.MycategoryViewHolder>() {
    class MycategoryViewHolder(var binding: CategoryitemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MycategoryViewHolder {
        return MycategoryViewHolder(
            CategoryitemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = categoryList.size

    override fun onBindViewHolder(holder: MycategoryViewHolder, position: Int) {
        val dataList = categoryList[position]
        holder.binding.categoryimage.setImageResource(dataList.catImage)
        holder.binding.category.text = dataList.catText
        holder.binding.categorybtn.setOnClickListener {
            var intent = Intent(requireActivity, QuizActivity::class.java)
            intent.putExtra("categoryimg", dataList.catImage)
            intent.putExtra("questiontype", dataList.catText)
            requireActivity.startActivity(intent)
        }
    }
}