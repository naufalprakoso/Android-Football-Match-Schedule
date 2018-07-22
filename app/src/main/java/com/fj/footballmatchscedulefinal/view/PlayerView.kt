package com.fj.footballmatchscedulefinal.view

import com.fj.footballmatchscedulefinal.model.Player

interface PlayerView{
    fun showLoading()
    fun hideLoading()
    fun showPlayerList(data: List<Player>)
}