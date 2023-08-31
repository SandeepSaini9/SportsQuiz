package com.example.sportsquiz.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsquiz.adapter.HistoryAdapter
import com.example.sportsquiz.databinding.FragmentHistoryBinding
import com.example.sportsquiz.model.HistoryModelClass
import com.example.sportsquiz.model.Users
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.Collections

class HistoryFragment : Fragment() {
    val binding by lazy {
        FragmentHistoryBinding.inflate(layoutInflater)
    }
    lateinit var adaptor:HistoryAdapter
    private var ListHistory = ArrayList<HistoryModelClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.database.reference.child("Player Coins History")
            .child(Firebase.auth.currentUser!!.uid).addValueEventListener(
                object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        ListHistory.clear()
                        var ListHistory1 = ArrayList<HistoryModelClass>()
                        for (datasnapshot in snapshot.children){
                          var data = datasnapshot.getValue(HistoryModelClass::class.java)
                            ListHistory1.add(data!!)
                        }
                        Collections.reverse(ListHistory1)
                        ListHistory.addAll(ListHistory1)
                        adaptor.notifyDataSetChanged()
                    }

                    override fun onCancelled(error: DatabaseError) {
                       // TODO("Not yet implemented")
                    }

                }
            )
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
        binding.HistoryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
         adaptor = HistoryAdapter(ListHistory)
        binding.HistoryRecyclerView.adapter = adaptor
        binding.HistoryRecyclerView.setHasFixedSize(true)
        Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var user = snapshot.getValue<Users>()
                        binding.name.text = user?.name
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // TODO("Not yet implemented")
                    }

                }

            )
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
        return binding.root
    }

    companion object {

    }
}