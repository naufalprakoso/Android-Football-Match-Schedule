package com.fj.footballmatchscedulefinal.presenter

import com.fj.footballmatchscedulefinal.TestContextProvider
import com.fj.footballmatchscedulefinal.api.APIRepository
import com.fj.footballmatchscedulefinal.api.TheSportDBApi
import com.fj.footballmatchscedulefinal.model.Team
import com.fj.footballmatchscedulefinal.response.TeamResponse
import com.fj.footballmatchscedulefinal.view.TeamView
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TestTeamPresenter{
    @Test
    fun getMatchList() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val league = "English Premiere League"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeams(league)),
                TeamResponse::class.java
        )).thenReturn(response)

        presenter.getTeamList(league)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showTeamList(teams)
        Mockito.verify(view).hideLoading()
    }

    @Mock
    private lateinit var view: TeamView
    @Mock
    private lateinit var gson: Gson
    @Mock
    private lateinit var apiRepository: APIRepository
    @Mock
    private lateinit var presenter:  TeamPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = TeamPresenter(view, apiRepository, gson, TestContextProvider())
    }
}