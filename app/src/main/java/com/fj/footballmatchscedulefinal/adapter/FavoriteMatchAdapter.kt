package com.fj.footballmatchscedulefinal.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.fj.footballmatchscedulefinal.R
import com.fj.footballmatchscedulefinal.model.FavoriteMatch
import org.jetbrains.anko.sdk25.listeners.onClick
import android.view.View
import kotlinx.android.synthetic.main.item_match.view.*

class FavoriteMatchAdapter (private val context: Context, private val events: List<FavoriteMatch>, private val listener: (FavoriteMatch) -> Unit)
    : RecyclerView.Adapter<FavoriteMatchAdapter.MatchViewHolder>(){

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bindItems(events[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder  =
            MatchViewHolder(LayoutInflater.from(context).inflate(R.layout.item_match, parent, false))

    override fun getItemCount(): Int = events.size

    class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(events: FavoriteMatch, listener: (FavoriteMatch) -> Unit){
            itemView.txt_team1.text = events.teamHomeName
            itemView.txt_team2.text = events.teamAwayName
            if(events.teamHomeScore != null){
                itemView.txt_score.text = events.teamHomeScore + " VS " + events.teamAwayScore
            }else{
                itemView.txt_score.text = "? VS ?"
            }
            itemView.txt_date.text = events.eventDate
            itemView.cv_item.onClick{
                listener(events)
            }
        }
    }
}