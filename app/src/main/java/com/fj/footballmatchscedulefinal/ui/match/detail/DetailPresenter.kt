package com.fj.footballmatchscedulefinal.ui.match.detail

import com.fj.footballmatchscedulefinal.utils.CoroutineContextProvider
import com.fj.footballmatchscedulefinal.api.APIRepository
import com.fj.footballmatchscedulefinal.api.TheSportDBApi
import com.fj.footballmatchscedulefinal.ui.match.MatchResponse
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailPresenter(private val view: MatchDetailView,
                      private val apiRepository: APIRepository,
                      private val gson: Gson,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()){

    fun getBadgeList(team: String?, team2: String?){
        view.showLoading()

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getHomeBadge(team)),
                        MatchResponse::class.java
                )
            }
            val data2 = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getAwayBadge(team2)),
                        MatchResponse::class.java
                )
            }

            view.showMatchList(data.await().teamId, data2.await().teamId)
            view.hideLoading()
        }
    }
}