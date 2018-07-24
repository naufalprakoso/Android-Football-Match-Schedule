package com.fj.footballmatchscedulefinal

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.fj.footballmatchscedulefinal.api.APIRepository
import com.fj.footballmatchscedulefinal.data.KEY
import com.fj.footballmatchscedulefinal.model.Player
import com.fj.footballmatchscedulefinal.presenter.PlayerDetailPresenter
import com.fj.footballmatchscedulefinal.view.PlayerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_player_detail.*
import org.jetbrains.anko.support.v4.onRefresh
import com.fj.footballmatchscedulefinal.R.string.*

class PlayerDetailActivity : AppCompatActivity(), PlayerView {

    private lateinit var presenter: PlayerDetailPresenter
    private lateinit var teams: Player
    private lateinit var id: String

    override fun showLoading() {
        progressbar.visible()
    }

    override fun hideLoading() {
        progressbar.invisible()
    }

    @SuppressLint("SetTextI18n")
    override fun showPlayerList(data: List<Player>) {
        teams = Player(data[0].idPlayer,
                data[0].strPlayer,
                data[0].strNationality,
                data[0].strTeam,
                data[0].strDescriptionEN,
                data[0].strPosition,
                data[0].strHeight,
                data[0].strWeight,
                data[0].strCutout)
        swipe.isRefreshing = false
        Picasso.with(this).load(data[0].strCutout).into(img_header)
        txt_name.text = data[0].strPlayer
        txt_description.text = data[0].strDescriptionEN
        txt_position_team.text = "${data[0].strPosition} at ${data[0].strTeam}"
        txt_nation.text = data[0].strNationality
        txt_weight.text = data[0].strWeight
        txt_height.text = data[0].strHeight
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        val intent = intent
        id = intent.getStringExtra(KEY.PLAYER_ID_KEY)
        supportActionBar?.title = getString(player_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val request = APIRepository()
        val gson = Gson()
        presenter = PlayerDetailPresenter(this, request, gson)
        presenter.getPlayerDetail(id)

        swipe.onRefresh {
            presenter.getPlayerDetail(id)
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
