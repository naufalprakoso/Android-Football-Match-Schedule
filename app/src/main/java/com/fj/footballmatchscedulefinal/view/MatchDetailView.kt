package com.fj.footballmatchscedulefinal.view

import com.fj.footballmatchscedulefinal.model.TeamMatch

interface MatchDetailView{
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<TeamMatch>?, data2: List<TeamMatch>?)
}