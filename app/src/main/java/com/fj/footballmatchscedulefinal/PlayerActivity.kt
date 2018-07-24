package com.fj.footballmatchscedulefinal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.fj.footballmatchscedulefinal.adapter.PlayerAdapter
import com.fj.footballmatchscedulefinal.api.APIRepository
import com.fj.footballmatchscedulefinal.model.Player
import com.fj.footballmatchscedulefinal.presenter.PlayerPresenter
import com.fj.footballmatchscedulefinal.view.PlayerView
import kotlinx.android.synthetic.main.activity_player.*
import com.fj.footballmatchscedulefinal.data.KEY
import com.google.gson.Gson
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh
import com.fj.footballmatchscedulefinal.R.string.*

class PlayerActivity : AppCompatActivity(), PlayerView {

    private var player: MutableList<Player> = mutableListOf()
    private lateinit var presenter: PlayerPresenter
    private lateinit var adapter: PlayerAdapter
    private lateinit var id: String

    override fun showLoading() {
        progressbar.visible()
    }

    override fun hideLoading() {
        progressbar.invisible()
    }

    override fun showPlayerList(data: List<Player>) {
        swipe.isRefreshing = false
        player.clear()
        player.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        supportActionBar?.title = getString(player_s)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val i = intent
        id = i.getStringExtra(KEY.TEAM_ID_KEY)

        adapter = PlayerAdapter(this, player) {
            startActivity<PlayerDetailActivity>(KEY.PLAYER_ID_KEY to "${it.idPlayer}")
        }
        rv_player.layoutManager = GridLayoutManager(this, 2)
        rv_player.adapter = adapter

        val request = APIRepository()
        val gson = Gson()
        presenter = PlayerPresenter(this, request, gson)
        presenter.getPlayerList(id)

        swipe.onRefresh {
            presenter.getPlayerList(id)
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
