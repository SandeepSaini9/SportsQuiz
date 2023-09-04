package com.example.sportsquiz

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.sportsquiz.databinding.ActivityQuizBinding
import com.example.sportsquiz.fragment.WithdrawalFragment
import com.example.sportsquiz.model.Questions
import com.example.sportsquiz.model.Users
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class QuizActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityQuizBinding.inflate(layoutInflater)
    }
    var currentChance = 0L
    var currentQuestion = 0
    var score = 0
    private lateinit var questionList: ArrayList<Questions>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Firebase.database.reference.child("Play Chance").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            currentChance = snapshot.value as Long
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

        questionList = ArrayList<Questions>()
        var image = intent.getIntExtra("categoryimg", 0)
        binding.categoryimg.setImageResource(image)
        var catText = intent.getStringExtra("questiontype")
        Firebase.firestore.collection("Questions").document(catText.toString())
            .collection("questions").get().addOnSuccessListener { questionData ->
                questionList.clear()
                for (data in questionData.documents) {
                    var question: Questions? = data.toObject(Questions::class.java)
                    questionList.add(question!!)

                }
                if (questionList.size > 0) {
                    binding.question.text = questionList.get(currentQuestion).question
                    binding.option1.text = questionList.get(currentQuestion).option1
                    binding.option2.text = questionList.get(currentQuestion).option2
                    binding.option3.text = questionList.get(currentQuestion).option3
                    binding.option4.text = questionList.get(currentQuestion).option4
                }
            }
        binding.categoryimg.setImageResource(image)
        binding.coinImage.setOnClickListener {
            val bottomSheetDialog: BottomSheetDialogFragment = WithdrawalFragment()
            bottomSheetDialog.show(this@QuizActivity.supportFragmentManager, "Test")
            bottomSheetDialog.enterTransition
        }
        binding.coinText.setOnClickListener {
            val bottomSheetDialog: BottomSheetDialogFragment = WithdrawalFragment()
            bottomSheetDialog.show(this@QuizActivity.supportFragmentManager, "Test")
            bottomSheetDialog.enterTransition
        }
        binding.option1.setOnClickListener {
            nextQuestionAndScoreUpdate(binding.option1.text.toString())
        }
        binding.option2.setOnClickListener {
            nextQuestionAndScoreUpdate(binding.option2.text.toString())
        }
        binding.option3.setOnClickListener {
            nextQuestionAndScoreUpdate(binding.option3.text.toString())
        }
        binding.option4.setOnClickListener {
            nextQuestionAndScoreUpdate(binding.option4.text.toString())
        }
    }

    private fun nextQuestionAndScoreUpdate(s: String) {
        if (s.equals(questionList.get(currentQuestion).answer)) {
            score += 10
        }
        currentQuestion++

        if (currentQuestion >= questionList.size) {
            //if score is above 60 percent
            if (score>=(score/(questionList.size*10))*100) {
                binding.Sorry.visibility = View.VISIBLE
                Firebase.database.reference.child("Play Chance")
                    .child(Firebase.auth.currentUser!!.uid).setValue(currentChance - 1)
                var isUpdated = false
                if (isUpdated) {

                }
            }else {
                binding.Winner.visibility = View.VISIBLE
                Firebase.database.reference.child("Play Chance")
                    .child(Firebase.auth.currentUser!!.uid).setValue(currentChance + 1)
                var isUpdated = false

            }
        } else {
            binding.question.text = questionList.get(currentQuestion).question
            binding.option1.text = questionList.get(currentQuestion).option1
            binding.option2.text = questionList.get(currentQuestion).option2
            binding.option3.text = questionList.get(currentQuestion).option3
            binding.option4.text = questionList.get(currentQuestion).option4
        }
    }
}