package com.example.sportsquiz.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsquiz.R
import com.example.sportsquiz.adapter.HistoryAdapter
import com.example.sportsquiz.databinding.FragmentHistoryBinding
import com.example.sportsquiz.model.HistoryModelClass

class HistoryFragment : Fragment() {
    val binding by lazy {
        FragmentHistoryBinding.inflate(layoutInflater)
    }
    private var ListHistory=ArrayList<HistoryModelClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ListHistory.add(HistoryModelClass("12:03","200"))
        ListHistory.add(HistoryModelClass("05:46","200"))
        ListHistory.add(HistoryModelClass("11:50","500"))
        ListHistory.add(HistoryModelClass("09:03","100"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.HistoryRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        var adaptor=HistoryAdapter(ListHistory)
        binding.HistoryRecyclerView.adapter=adaptor
        binding.HistoryRecyclerView.setHasFixedSize(true)
        return binding.root
    }

    companion object {

    }
}