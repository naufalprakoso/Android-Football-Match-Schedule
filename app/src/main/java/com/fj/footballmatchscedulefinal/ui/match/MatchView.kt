package com.fj.footballmatchscedulefinal.ui.match

import com.fj.footballmatchscedulefinal.model.Match

interface MatchView{
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<Match>?)
}