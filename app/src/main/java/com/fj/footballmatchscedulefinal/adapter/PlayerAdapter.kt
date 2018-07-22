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

class PlayerAdapter(private val context: Context, private val player: List<Player>, private val listener: (Player) -> Unit)
    : RecyclerView.Adapter<PlayerAdapter.TeamViewHolder>(){

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(player[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder =
            TeamViewHolder(LayoutInflater.from(context).inflate(R.layout.item_player, parent, false))

    override fun getItemCount(): Int = player.size

    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(player: Player, listener: (Player) -> Unit){
            if (player.strCutout == null){
                Picasso.with(itemView.context).load(
                        "https://payload177.cargocollective.com/1/8/277497/5848345/Screen-Shot-2014-07-15-at-12.56.03-PM.png"
                ).into(itemView.img_player)
            }else{
                Picasso.with(itemView.context).load(player.strCutout).into(itemView.img_player)
            }

            itemView.txt_name.text = player.strPlayer
            itemView.txt_position.text = player.strPosition

            itemView.cv_item.onClick{
                listener(player)
            }
        }
    }
}