package com.fj.footballmatchscedulefinal.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fj.footballmatchscedulefinal.R
import com.fj.footballmatchscedulefinal.model.Player
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_player.view.*
import org.jetbrains.anko.sdk25.listeners.onClick

class PlayerAdapter(private val context: Context, private val players: List<Player>, private val listener: (Player) -> Unit)
    : RecyclerView.Adapter<PlayerAdapter.TeamViewHolder>(){

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(players[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder =
            TeamViewHolder(LayoutInflater.from(context).inflate(R.layout.item_player, parent, false))

    override fun getItemCount(): Int = players.size

    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(player: Player, listener: (Player) -> Unit){
            Picasso.with(itemView.context).load(player.strFanart3).into(itemView.img_player)
            itemView.txt_position.text = player.strPosition

            itemView.cv_item.onClick{
                listener(player)
            }
        }
    }
}