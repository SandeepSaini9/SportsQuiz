package com.example.sportsquiz.fragment

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
import com.example.sportsquiz.model.Users
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {
    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    private var categoryList = ArrayList<CategoryModelClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryList.add(CategoryModelClass(R.drawable.scince, "Science"))
        categoryList.add(CategoryModelClass(R.drawable.mathmetic, "Math"))
        categoryList.add(CategoryModelClass(R.drawable.englishs, "english"))
        categoryList.add(CategoryModelClass(R.drawable.englishs, "english"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.coinImage.setOnClickListener {
            val bottomSheetDialog: BottomSheetDialogFragment = WithdrawalFragment()
            bottomSheetDialog.show(requireActivity().supportFragmentManager, "Test")
            bottomSheetDialog.enterTransition
        }
        binding.coinText.setOnClickListener {
            val bottomSheetDialog: BottomSheetDialogFragment = WithdrawalFragment()
            bottomSheetDialog.show(requireActivity().supportFragmentManager, "Test")
            bottomSheetDialog.enterTransition
        }
        Firebase.database.reference.child("Player Coins").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            var currentCoins = snapshot.getValue() as Long
                            binding.coinText.text = currentCoins.toString()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                }
            )

        Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var user = snapshot.getValue<Users>()
                        binding.name.text = user?.name.toString()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // TODO("Not yet implemented")
                    }

                }

            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.categoryRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        var adapter = CategoryAdapter(categoryList, requireActivity())
        binding.categoryRecyclerView.adapter = adapter
        binding.categoryRecyclerView.setHasFixedSize(true)
    }

    companion object {
    }
}