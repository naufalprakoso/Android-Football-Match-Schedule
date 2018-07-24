package com.fj.footballmatchscedulefinal.presenter

import com.fj.footballmatchscedulefinal.TestContextProvider
import com.fj.footballmatchscedulefinal.api.APIRepository
import com.fj.footballmatchscedulefinal.api.TheSportDBApi
import com.fj.footballmatchscedulefinal.model.Player
import com.fj.footballmatchscedulefinal.response.PlayerResponse
import com.fj.footballmatchscedulefinal.response.TeamResponse
import com.fj.footballmatchscedulefinal.view.PlayerView
import com.fj.footballmatchscedulefinal.view.TeamView
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TestPlayerPresenter{
    @Test
    fun getMatchList() {
        val teams: MutableList<Player> = mutableListOf()
        val response = PlayerResponse(teams)
        val league = "34145937"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPlayer(league)),
                PlayerResponse::class.java
        )).thenReturn(response)

        presenter.getPlayerList(league)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showPlayerList(teams)
        Mockito.verify(view).hideLoading()
    }

    @Mock
    private lateinit var view: PlayerView
    @Mock
    private lateinit var gson: Gson
    @Mock
    private lateinit var apiRepository: APIRepository
    @Mock
    private lateinit var presenter:  PlayerPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = PlayerPresenter(view, apiRepository, gson, TestContextProvider())
    }
}