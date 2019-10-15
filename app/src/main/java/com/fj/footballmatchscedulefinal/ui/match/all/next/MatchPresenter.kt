package com.fj.footballmatchscedulefinal.ui.match.all.next

import com.fj.footballmatchscedulefinal.utils.CoroutineContextProvider
import com.fj.footballmatchscedulefinal.api.APIRepository
import com.fj.footballmatchscedulefinal.api.TheSportDBApi
import com.fj.footballmatchscedulefinal.ui.match.MatchResponse
import com.fj.footballmatchscedulefinal.ui.match.MatchView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MatchPresenter(private val view: MatchView,
                     private val apiRepository: APIRepository,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()){

    fun getMatchList(match: String?){
        view.showLoading()

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getPastMatch(match)),
                        MatchResponse::class.java
                )
            }

            view.showMatchList(data.await().events)
            view.hideLoading()
        }
    }
}