package com.fj.footballmatchscedulefinal.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fj.footballmatchscedulefinal.R
import com.fj.footballmatchscedulefinal.model.Match
import kotlinx.android.synthetic.main.item_match.view.*
import org.jetbrains.anko.sdk25.listeners.onClick

class MatchAdapter(private val context: Context, private val events: List<Match>, private val listener: (Match) -> Unit)
    : RecyclerView.Adapter<MatchAdapter.MatchViewHolder>(){

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bindItems(events[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder  =
            MatchViewHolder(LayoutInflater.from(context).inflate(R.layout.item_match, parent, false))

    override fun getItemCount(): Int = events.size

    class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bindItems(events: Match, listener: (Match) -> Unit){
            itemView.txt_team1.text = events.homeName
            itemView.txt_team2.text = events.awayName
            if(events.homeScore != null){
                itemView.txt_score.text = "${events.homeScore} VS ${events.awayScore}"
            }else{
                itemView.txt_score.text = "? VS ?"
            }
            itemView.txt_date.text = events.dateEvent
            itemView.cv_item.onClick{
                listener(events)
            }
        }
    }
}