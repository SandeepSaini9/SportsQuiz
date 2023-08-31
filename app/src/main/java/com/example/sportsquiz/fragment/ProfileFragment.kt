package com.example.sportsquiz.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sportsquiz.R
import com.example.sportsquiz.databinding.FragmentProfileBinding
import com.example.sportsquiz.model.Users
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {
    val binding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }
    private var isExpand = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.imageButton2.setOnClickListener {
            if (isExpand) {
                binding.expandableconstraintLayout.visibility = View.VISIBLE
                binding.imageButton2.setImageResource(R.drawable.arrowup)
            } else {
                binding.expandableconstraintLayout.visibility = View.GONE
                binding.imageButton2.setImageResource(R.drawable.downarrow)

            }
            isExpand = !isExpand
        }
        Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue<Users>()
                        binding.name.text = user?.name
                        binding.NameUp.text = user?.name
                        binding.Password.text = user?.password
                        binding.Email.text = user?.email
                        binding.Age.text = user?.age.toString()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // TODO("Not yet implemented")
                    }

                }

            )
        return binding.root
    }

    companion object {
    }
}