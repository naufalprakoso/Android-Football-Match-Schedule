package com.fj.footballmatchscedulefinal.presenter

import com.fj.footballmatchscedulefinal.TestContextProvider
import com.fj.footballmatchscedulefinal.api.APIRepository
import com.fj.footballmatchscedulefinal.api.TheSportDBApi
import com.fj.footballmatchscedulefinal.model.Match
import com.fj.footballmatchscedulefinal.response.MatchResponse
import com.fj.footballmatchscedulefinal.view.MatchView
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MatchPastPresenter {
    @Test
    fun getMatchList() {
        val teams: MutableList<Match> = mutableListOf()
        val response = MatchResponse(teams)
        val league = "English Premiere League"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPastMatch(league)),
                MatchResponse::class.java
        )).thenReturn(response)

        presenter.getMatchList(league)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showMatchList(teams)
        Mockito.verify(view).hideLoading()
    }

    @Mock
    private lateinit var view: MatchView
    @Mock
    private lateinit var gson: Gson
    @Mock
    private lateinit var apiRepository: APIRepository
    @Mock
    private lateinit var presenter:  MatchPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view, apiRepository, gson, TestContextProvider())
    }
}