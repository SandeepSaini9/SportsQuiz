package com.example.sportsquiz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsquiz.databinding.HistoryitemBinding
import com.example.sportsquiz.model.HistoryModelClass
import java.sql.Date
import java.sql.Timestamp

class HistoryAdapter(var ListHistory: ArrayList<HistoryModelClass>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryCoinViewHolder>() {
    class HistoryCoinViewHolder(var binding: HistoryitemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryCoinViewHolder {
        return HistoryCoinViewHolder(
            HistoryitemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = ListHistory.size

    override fun onBindViewHolder(holder: HistoryCoinViewHolder, position: Int) {
        var timeStamp = Timestamp(ListHistory.get(position).timeAndDate.toLong())
        holder.binding.Time.text = Date(timeStamp.time).toString()
        holder.binding.status.text = if (ListHistory.get(position).isWithdrawal) {
            "- Money Withdrawal"
        } else {
            "+ Money Added"
        }
        holder.binding.Coin.text = ListHistory[position].coin

    }
}