package com.fj.footballmatchscedulefinal.ui.team

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fj.footballmatchscedulefinal.R
import com.fj.footballmatchscedulefinal.model.Team
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_team.view.*
import org.jetbrains.anko.sdk25.listeners.onClick

class TeamAdapter (private val context: Context, private val teams: List<Team>, private val listener: (Team) -> Unit)
    : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>(){

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder =
            TeamViewHolder(LayoutInflater.from(context).inflate(R.layout.item_team, parent, false))

    override fun getItemCount(): Int = teams.size

    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(teams: Team, listener: (Team) -> Unit){
            Picasso.with(itemView.context).load(teams.teamBadge).into(itemView.img_team)
            itemView.txt_team.text = teams.teamName

            itemView.cv_item.onClick{
                listener(teams)
            }
        }
    }
}