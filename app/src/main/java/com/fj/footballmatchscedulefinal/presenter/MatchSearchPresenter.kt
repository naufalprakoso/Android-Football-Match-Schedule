package com.fj.footballmatchscedulefinal.presenter

import com.fj.footballmatchscedulefinal.CoroutineContextProvider
import com.fj.footballmatchscedulefinal.api.APIRepository
import com.fj.footballmatchscedulefinal.api.TheSportDBApi
import com.fj.footballmatchscedulefinal.response.MatchResponse
import com.fj.footballmatchscedulefinal.response.MatchSearchResponse
import com.fj.footballmatchscedulefinal.view.MatchView
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