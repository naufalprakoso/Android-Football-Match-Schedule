package com.fj.footballmatchscedulefinal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.fj.footballmatchscedulefinal.R.array.league
import com.fj.footballmatchscedulefinal.adapter.TeamAdapter
import com.fj.footballmatchscedulefinal.api.APIRepository
import com.fj.footballmatchscedulefinal.model.Team
import com.fj.footballmatchscedulefinal.presenter.TeamPresenter
import com.fj.footballmatchscedulefinal.view.TeamView
import com.fj.footballmatchscedulefinal.data.KEY
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_team.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.startActivity

class TeamActivity : AppCompatActivity(), TeamView, View.OnClickListener {

    override fun onClick(p0: View?) {
        when(p0){
            btn_search -> {
                val strSearch = edt_search.text.toString()

                when{
                    strSearch.isEmpty() -> edt_search.error = "Must be filled"
                    else -> {
                        startActivity<TeamSearchActivity>(
                                KEY.TEAM_NAME_KEY to strSearch
                        )
                    }
                }
            }
        }
    }

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamPresenter
    private lateinit var adapter: TeamAdapter
    private lateinit var leagueName: String

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
        setContentView(R.layout.activity_team)

        btn_search.setOnClickListener(this)

        val spinnerItems = resources.getStringArray(league)
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner_team.adapter = spinnerAdapter

        adapter = TeamAdapter(this, teams) {
            startActivity<TeamDetailActivity>(KEY.TEAM_ID_KEY to "${it.teamId}")
        }
        rv_team.layoutManager = GridLayoutManager(this, 2)
        rv_team.adapter = adapter

        val request = APIRepository()
        val gson = Gson()
        presenter = TeamPresenter(this, request, gson)
        spinner_team.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinner_team.selectedItem.toString()
                presenter.getTeamList(leagueName)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        swipe.onRefresh {
            presenter.getTeamList(leagueName)
        }
    }

    private fun View.visible(){
        visibility = View.VISIBLE
    }

    private fun View.invisible(){
        visibility = View.INVISIBLE
    }
}
