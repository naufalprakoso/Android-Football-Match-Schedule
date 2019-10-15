package com.fj.footballmatchscedulefinal.ui.team.detail

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.fj.footballmatchscedulefinal.ui.team.player.all.PlayerActivity
import com.fj.footballmatchscedulefinal.R
import com.fj.footballmatchscedulefinal.api.APIRepository
import com.fj.footballmatchscedulefinal.model.Team
import com.fj.footballmatchscedulefinal.ui.team.TeamView
import com.fj.footballmatchscedulefinal.data.Const
import com.fj.footballmatchscedulefinal.db.database
import com.fj.footballmatchscedulefinal.model.FavoriteTeam
import com.google.gson.Gson
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_team_detail.*
import kotlinx.android.synthetic.main.content_team_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh
import com.fj.footballmatchscedulefinal.R.string.*

class TeamDetailActivity : AppCompatActivity(), TeamView, View.OnClickListener {

    override fun onClick(p0: View?) {
        when(p0){
            btn_view_player -> {
                startActivity<PlayerActivity>(
                        Const.TEAM_ID_KEY to id
                )
            }
        }
    }

    private lateinit var presenter: TeamDetailPresenter
    private lateinit var teams: Team

    private var isFavorite: Boolean = false
    private lateinit var id: String

    override fun showLoading() {
        progressbar.visible()
    }

    override fun hideLoading() {
        progressbar.invisible()
    }

    @SuppressLint("SetTextI18n")
    override fun showTeamList(data: List<Team>) {
        teams = Team(data[0].teamId,
                data[0].teamName,
                data[0].teamBadge)
        swipe.isRefreshing = false
        Picasso.with(this).load(data[0].teamBadge).into(img_team)
        txt_name.text = data[0].teamName
        txt_description.text = data[0].teamDescription
        txt_formed.text = "Since ${data[0].teamFormedYear}"
        txt_stadium.text = "${data[0].teamStadium}"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)
        setSupportActionBar(toolbar)

        btn_view_player.setOnClickListener(this)

        val intent = intent
        id = intent.getStringExtra(Const.TEAM_ID_KEY)

        favoriteState()
        setFavorite()

        supportActionBar?.title = getString(title_activity_team_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener {
            if (isFavorite) removeFromFavorite() else addToFavorite()

            isFavorite = !isFavorite
            setFavorite()
        }

        val request = APIRepository()
        val gson = Gson()
        presenter = TeamDetailPresenter(this, request, gson)
        presenter.getTeamDetail(id)

        swipe.onRefresh {
            presenter.getTeamDetail(id)
        }
    }

    private fun View.visible(){
        visibility = View.VISIBLE
    }

    private fun View.invisible(){
        visibility = View.INVISIBLE
    }

    private fun favoriteState(){
        database.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE)
                    .whereArgs("TEAM_ID = {id}",
                            "id" to id)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            isFavorite = favorite.isNotEmpty()
        }
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(FavoriteTeam.TABLE_FAVORITE,
                        FavoriteTeam.TEAM_ID to teams.teamId,
                        FavoriteTeam.TEAM_NAME to teams.teamName,
                        FavoriteTeam.TEAM_BADGE to teams.teamBadge)
            }
            snackbar(swipe, getString(added_fav)).show()
        } catch (e: SQLiteConstraintException){
            snackbar(swipe, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(FavoriteTeam.TABLE_FAVORITE, "TEAM_ID = {id}",
                        "id" to id)
            }
            snackbar(swipe, getString(remove_fav)).show()
        } catch (e: SQLiteConstraintException){
            snackbar(swipe, e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite){
            fab.setImageResource(R.drawable.ic_added_to_favorites)
        }
        else{
            fab.setImageResource(R.drawable.ic_add_to_favorites)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
