package com.example.sportsquiz.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sportsquiz.R
import com.example.sportsquiz.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    val binding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }
    var isExpand =true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.imageButton2.setOnClickListener{
            if (isExpand){
                binding.expandableconstraintLayout.visibility=View.VISIBLE
                binding.imageButton2.setImageResource(R.drawable.arrowup)
            }
            else{
                binding.expandableconstraintLayout.visibility=View.GONE
                binding.imageButton2.setImageResource(R.drawable.downarrow)

            }
            isExpand =! isExpand
        }
        return binding.root
    }

    companion object {
    }
}