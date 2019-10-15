package com.fj.footballmatchscedulefinal.ui.team.player.detail

import com.fj.footballmatchscedulefinal.utils.CoroutineContextProvider
import com.fj.footballmatchscedulefinal.api.APIRepository
import com.fj.footballmatchscedulefinal.api.TheSportDBApi
import com.fj.footballmatchscedulefinal.ui.team.player.PlayerView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerDetailPresenter(private val view: PlayerView,
                            private val apiRepository: APIRepository,
                            private val gson: Gson,
                            private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    fun getPlayerDetail(players: String) {
        view.showLoading()

        async(context.main) {
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