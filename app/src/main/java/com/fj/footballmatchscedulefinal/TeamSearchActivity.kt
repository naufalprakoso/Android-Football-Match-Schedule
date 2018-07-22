package com.fj.footballmatchscedulefinal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.fj.footballmatchscedulefinal.adapter.TeamAdapter
import com.fj.footballmatchscedulefinal.api.APIRepository
import com.fj.footballmatchscedulefinal.data.KEY
import com.fj.footballmatchscedulefinal.model.Team
import com.fj.footballmatchscedulefinal.presenter.TeamSearchPresenter
import com.fj.footballmatchscedulefinal.view.TeamView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_team_search.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh

class TeamSearchActivity : AppCompatActivity(), TeamView {

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamSearchPresenter
    private lateinit var adapter: TeamAdapter

    override fun showLoading() {
        progressbar.visible()
    }

    override fun hideLoading() {
        progressbar.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        swipe.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_search)

        val i = intent
        val getName = i.getStringExtra(KEY.TEAM_NAME_KEY)

        txt_search.setText(getName)

        adapter = TeamAdapter(this, teams) {
            startActivity<TeamDetailActivity>(KEY.TEAM_ID_KEY to "${it.teamId}")
        }
        rv_team.layoutManager = GridLayoutManager(this, 2)
        rv_team.adapter = adapter

        val request = APIRepository()
        val gson = Gson()
        presenter = TeamSearchPresenter(this, request, gson)
        presenter.getTeamList(getName)

        swipe.onRefresh {
            presenter.getTeamList(getName)
        }
    }

    private fun View.visible(){
        visibility = View.VISIBLE
    }

    private fun View.invisible(){
        visibility = View.INVISIBLE
    }
}
