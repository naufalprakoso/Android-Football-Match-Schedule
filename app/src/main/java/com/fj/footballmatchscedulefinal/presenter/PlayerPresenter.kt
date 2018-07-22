package com.fj.footballmatchscedulefinal.presenter

import com.fj.footballmatchscedulefinal.view.PlayerView
import com.fj.footballmatchscedulefinal.CoroutineContextProvider
import com.fj.footballmatchscedulefinal.api.APIRepository
import com.fj.footballmatchscedulefinal.api.TheSportDBApi
import com.fj.footballmatchscedulefinal.response.PlayerResponse
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerPresenter(private val view: PlayerView,
                    private val apiRepository: APIRepository,
                    private val gson: Gson,
                    private val context: CoroutineContextProvider = CoroutineContextProvider()){
    fun getPlayerList(player: String?) {
        view.showLoading()

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getPlayer(player)),
                        PlayerResponse::class.java
                )
            }
            view.showPlayerList(data.await().player)
            view.hideLoading()
        }
    }
}