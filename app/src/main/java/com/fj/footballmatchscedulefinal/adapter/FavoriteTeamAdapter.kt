package com.fj.footballmatchscedulefinal.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fj.footballmatchscedulefinal.R
import com.fj.footballmatchscedulefinal.model.FavoriteTeam
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_team.view.*
import org.jetbrains.anko.sdk25.listeners.onClick

class FavoriteTeamAdapter(private val context: Context, private val favorite: List<FavoriteTeam>, private val listener: (FavoriteTeam) -> Unit)
    : RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
            FavoriteViewHolder(LayoutInflater.from(context).inflate(R.layout.item_team, parent, false))

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }

    override fun getItemCount(): Int = favorite.size
}

class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view){
    fun bindItem(favorite: FavoriteTeam, listener: (FavoriteTeam) -> Unit) {
        Picasso.with(itemView.context).load(favorite.teamBadge).into(itemView.img_team)
        itemView.txt_team.text = favorite.teamName
        itemView.onClick { listener(favorite) }
    }
}