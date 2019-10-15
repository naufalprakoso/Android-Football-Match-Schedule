package com.fj.footballmatchscedulefinal.ui.match.search

import com.fj.footballmatchscedulefinal.utils.CoroutineContextProvider
import com.fj.footballmatchscedulefinal.api.APIRepository
import com.fj.footballmatchscedulefinal.api.TheSportDBApi
import com.fj.footballmatchscedulefinal.ui.match.MatchView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MatchSearchPresenter(private val view: MatchView,
                           private val apiRepository: APIRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()){
    fun getMatchList(event: String?) {
        view.showLoading()

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getMatchSearch(event)),
                        MatchSearchResponse::class.java //TODO:Belum di edit
                )
            }
            view.showMatchList(data.await().event)
            view.hideLoading()
        }
    }
}