package com.fj.footballmatchscedulefinal.presenter

import com.fj.footballmatchscedulefinal.api.APIRepository
import com.fj.footballmatchscedulefinal.api.TheSportDBApi
import com.fj.footballmatchscedulefinal.response.PlayerDetailResponse
import com.fj.footballmatchscedulefinal.response.PlayerResponse
import com.fj.footballmatchscedulefinal.view.PlayerView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerDetailPresenter(private val view: PlayerView,
                          private val apiRepository: APIRepository,
                          private val gson: Gson) {
    fun getPlayerDetail(players: String) {
        view.showLoading()

        async(UI) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getPlayerDetail(players)),
                        PlayerDetailResponse::class.java
                )
            }
            view.showPlayerList(data.await().players)
            view.hideLoading()
        }
    }
}