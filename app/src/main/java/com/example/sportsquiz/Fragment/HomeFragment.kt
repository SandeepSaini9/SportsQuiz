package com.example.sportsquiz.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sportsquiz.R
import com.example.sportsquiz.adapter.CategoryAdapter
import com.example.sportsquiz.databinding.FragmentHomeBinding
import com.example.sportsquiz.model.CategoryModelClass

class HomeFragment : Fragment() {
    private val binding:FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    private var categoryList=ArrayList<CategoryModelClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryList.add(CategoryModelClass(R.drawable.scince,"Science"))
        categoryList.add(CategoryModelClass(R.drawable.mathmetic,"Math"))
        categoryList.add(CategoryModelClass(R.drawable.englishs,"English"))
        categoryList.add(CategoryModelClass(R.drawable.englishs,"English"))
        binding.categoryRecyclerView.layoutManager=GridLayoutManager(requireContext(),2)
        var adapter = CategoryAdapter(categoryList)
        binding.categoryRecyclerView.adapter=adapter
        binding.categoryRecyclerView.setHasFixedSize(true)

    }
    companion object {

    }
}