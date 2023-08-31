package com.example.sportsquiz.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.sportsquiz.databinding.FragmentSpinBinding
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
import java.util.Random

class SpinFragment : Fragment() {
    private lateinit var binding: FragmentSpinBinding
    private lateinit var timer: CountDownTimer
    private val itemTitles = arrayOf("100", "Try Again", "500", "Try Again", "200", "Try Again")
    var currentChance = 0L
    var currentCoins = 0L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSpinBinding.inflate(inflater, container, false)
        Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue<Users>()
                        binding.name.text = user?.name
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // TODO("Not yet implemented")
                    }

                }

            )
        Firebase.database.reference.child("Play Chance").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            currentChance = snapshot.value as Long
                            binding.spinChance.text = (snapshot.value as Long).toString()

                        } else {
                            val temp = 0
                            binding.spinChance.text = temp.toString()
                            binding.spin.isEnabled = false
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                }
            )

        Firebase.database.reference.child("Player Coins").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            currentCoins = snapshot.value as Long
                            binding.coinText.text = currentCoins.toString()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                }
            )

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun showResult(itemTitle: String, spin: Int) {
        if (spin % 2 == 0) {
            val winCoin = itemTitle.toInt()
            Firebase.database.reference.child("Player Coins").child(Firebase.auth.currentUser!!.uid)
                .setValue(winCoin + currentCoins)
            val historyModelClass =
                HistoryModelClass(System.currentTimeMillis().toString(), winCoin.toString(), false)
            Firebase.database.reference.child("Player Coins History")
                .child(Firebase.auth.currentUser!!.uid).push()
                .setValue(historyModelClass)

            binding.coinText.text = (winCoin + currentCoins).toString()
        }
        Toast.makeText(requireContext(), itemTitle, Toast.LENGTH_SHORT).show()
        currentChance -= 1
        Firebase.database.reference.child("Play Chance").child(Firebase.auth.currentUser!!.uid)
            .setValue(currentChance)
        binding.spin.isEnabled = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        binding.spin.setOnClickListener {
            binding.spin.isEnabled = false

            if (currentChance > 0) {
                val spin = Random().nextInt(6)
                val degrees = 60f * spin
                timer = object : CountDownTimer(5000, 50) {
                    var rotation = 0f

                    override fun onTick(millisUntilFinished: Long) {
                        rotation += 5f
                        if (rotation >= degrees) {
                            rotation = degrees
                            timer.cancel()
                            showResult(itemTitles[spin], spin)
                        }
                        binding.wheel.rotation = rotation
                    }

                    override fun onFinish() {}
                }.start()
            } else {
                Toast.makeText(activity, "Out of Spin", Toast.LENGTH_SHORT).show()
            }
        }
    }
}