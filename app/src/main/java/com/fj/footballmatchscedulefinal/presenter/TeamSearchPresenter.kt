package com.fj.footballmatchscedulefinal.presenter

import com.fj.footballmatchscedulefinal.CoroutineContextProvider
import com.fj.footballmatchscedulefinal.api.APIRepository
import com.fj.footballmatchscedulefinal.api.TheSportDBApi
import com.fj.footballmatchscedulefinal.response.TeamResponse
import com.fj.footballmatchscedulefinal.view.TeamView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamSearchPresenter(private val view: TeamView,
                    private val apiRepository: APIRepository,
                    private val gson: Gson,
                    private val context: CoroutineContextProvider = CoroutineContextProvider()){
    fun getTeamList(teams: String?) {
        view.showLoading()

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeamSearch(teams)),
                        TeamResponse::class.java
                )
            }
            view.showTeamList(data.await().teams)
            view.hideLoading()
        }
    }
}