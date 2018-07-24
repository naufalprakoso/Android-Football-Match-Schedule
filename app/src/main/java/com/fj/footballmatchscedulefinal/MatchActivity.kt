package com.fj.footballmatchscedulefinal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.fj.footballmatchscedulefinal.adapter.MatchAdapter
import com.fj.footballmatchscedulefinal.model.Match
import com.fj.footballmatchscedulefinal.presenter.MatchPresenter
import com.fj.footballmatchscedulefinal.view.MatchView
import com.fj.footballmatchscedulefinal.R.string.*
import com.fj.footballmatchscedulefinal.R.array.league_match
import com.fj.footballmatchscedulefinal.api.APIRepository
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_match.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast
import com.fj.footballmatchscedulefinal.data.KEY

class MatchActivity : AppCompatActivity(), MatchView, View.OnClickListener {

    override fun onClick(p0: View?) {
        when(p0){
            btn_search -> {
                val strSearch = edt_search.text.toString()

                when{
                    strSearch.isEmpty() -> edt_search.error = "Must be filled"
                    else -> {
                        startActivity<MatchSearchActivity>(
                                KEY.MATCH_NAME_KEY to strSearch
                        )
                    }
                }
            }
        }
    }

    private lateinit var adapter: MatchAdapter
    private lateinit var pastPresenter: MatchPresenter
    private var events: MutableList<Match> = mutableListOf()

    override fun showLoading() {
        progressbar.visible()
    }

    override fun hideLoading() {
        progressbar.invisible()
    }

    override fun showMatchList(data: List<Match>?) {
        swipe.isRefreshing = false
        events.clear()
        data?.let {
            events.addAll(data)
            adapter.notifyDataSetChanged()
        } ?: toast(getString(no_data))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)

        supportActionBar?.title = "Past Match"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btn_search.setOnClickListener(this)

        val request = APIRepository()
        val gson = Gson()
        pastPresenter = MatchPresenter(this, request, gson)

        val spinnerItems = resources.getStringArray(league_match)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner_league.adapter = spinnerAdapter

        spinner_league.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val getResult: String = when {
                    spinner_league.selectedItem == getString(league_epl) -> {
                        getString(league_epl_api)
                    }
                    spinner_league.selectedItem == getString(league_elc) -> getString(league_elc_api)
                    spinner_league.selectedItem == getString(league_gb) -> getString(league_gb_api)
                    else -> getString(league_isa_api)
                }

                adapter = MatchAdapter(ctx, events) {
                    startActivity<MatchDetailActivity>(
                                KEY.HOME_ID_KEY to it.homeId,
                                KEY.AWAY_ID_KEY to it.awayId,
                                KEY.EVENT_ID_KEY to it.eventId)
                }

                rv_match.layoutManager = LinearLayoutManager(ctx)
                rv_match.adapter = adapter

                pastPresenter.getMatchList(getResult)

                swipe.onRefresh {
                    pastPresenter.getMatchList(getResult)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun View.visible(){
        visibility = View.VISIBLE
    }

    private fun View.invisible(){
        visibility = View.INVISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
