package com.fj.footballmatchscedulefinal.presenter

import com.fj.footballmatchscedulefinal.api.APIRepository
import com.fj.footballmatchscedulefinal.api.TheSportDBApi
import com.fj.footballmatchscedulefinal.response.TeamResponse
import com.fj.footballmatchscedulefinal.view.TeamView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamDetailPresenter(private val view: TeamView,
                          private val apiRepository: APIRepository,
                          private val gson: Gson) {

    fun getTeamDetail(teamId: String) {
        view.showLoading()

        async(UI) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeamDetail(teamId)),
                        TeamResponse::class.java
                )
            }
            view.showTeamList(data.await().teams)
            view.hideLoading()
        }
    }
}