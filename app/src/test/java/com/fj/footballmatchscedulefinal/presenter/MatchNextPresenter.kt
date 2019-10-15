package com.fj.footballmatchscedulefinal.presenter

import com.fj.footballmatchscedulefinal.utils.TestContextProvider
import com.fj.footballmatchscedulefinal.api.APIRepository
import com.fj.footballmatchscedulefinal.api.TheSportDBApi
import com.fj.footballmatchscedulefinal.model.Match
import com.fj.footballmatchscedulefinal.ui.match.MatchResponse
import com.fj.footballmatchscedulefinal.ui.match.MatchView
import com.fj.footballmatchscedulefinal.ui.match.all.past.NextPresenter
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MatchNextPresenter{
    @Test
    fun getMatchList() {
        val teams: MutableList<Match> = mutableListOf()
        val response = MatchResponse(teams)
        val league = "English Premiere League"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getNextMatch(league)),
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
    private lateinit var presenter: NextPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = NextPresenter(view, apiRepository, gson, TestContextProvider())
    }
}