package com.fj.footballmatchscedulefinal

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.fj.footballmatchscedulefinal.api.APIRepository
import com.fj.footballmatchscedulefinal.model.Match
import com.fj.footballmatchscedulefinal.model.Team
import com.fj.footballmatchscedulefinal.presenter.DetailPresenter
import com.fj.footballmatchscedulefinal.presenter.MatchDetailPresenter
import com.fj.footballmatchscedulefinal.view.MatchView
import com.fj.footballmatchscedulefinal.data.KEY
import com.fj.footballmatchscedulefinal.model.FavoriteMatch
import com.fj.footballmatchscedulefinal.model.TeamMatch
import com.fj.footballmatchscedulefinal.view.MatchDetailView
import com.google.gson.Gson
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_match_detail.*
import kotlinx.android.synthetic.main.content_match_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast
import com.fj.footballmatchscedulefinal.R.string.*
import com.fj.footballmatchscedulefinal.db.database

class MatchDetailActivity : AppCompatActivity(), MatchDetailView, MatchView {

    private lateinit var detailPresenter: DetailPresenter
    private lateinit var detailMatchPresenter: MatchDetailPresenter
    private lateinit var team: Team
    private lateinit var team2: Team
    private lateinit var match: Match

    private var isFavorite: Boolean = false

    private lateinit var homeId: String
    private lateinit var awayId: String
    private lateinit var eventId: String

    private lateinit var homeName: String
    private lateinit var awayName: String
    private lateinit var homeScore: String
    private lateinit var awayScore: String
    private lateinit var eventDate: String

    override fun showLoading() {
        progressbar.visible()
    }

    override fun hideLoading() {
        progressbar.invisible()
    }

    override fun showMatchList(data: List<TeamMatch>?, data2: List<TeamMatch>?) {
        team = Team(data?.get(0)?.teamBadge)
        team2 = Team(data2?.get(0)?.teamBadge)
        Picasso.with(this).load(data?.get(0)?.teamBadge).into(img_team1)
        Picasso.with(this).load(data2?.get(0)?.teamBadge).into(img_team2)
    }

    @SuppressLint("SetTextI18n")
    override fun showMatchList(data: List<Match>?) {
        match = Match(data?.get(0)?.eventId,
                data?.get(0)?.homeName,
                data?.get(0)?.awayName,
                data?.get(0)?.homeScore,
                data?.get(0)?.awayScore,
                data?.get(0)?.dateEvent,
                data?.get(0)?.homeGoalKeeper,
                data?.get(0)?.awayGoalKeeper,
                data?.get(0)?.homeGoalDetails,
                data?.get(0)?.awayGoalDetails,
                data?.get(0)?.homeShots,
                data?.get(0)?.awayShots,
                data?.get(0)?.homeDefense,
                data?.get(0)?.awayDefense,
                data?.get(0)?.homeMidfield,
                data?.get(0)?.awayMidfield,
                data?.get(0)?.homeForward,
                data?.get(0)?.awayForward,
                data?.get(0)?.homeSubstitutes,
                data?.get(0)?.awaySubstitutes,
                data?.get(0)?.homeFormation,
                data?.get(0)?.awayFormation,
                data?.get(0)?.teamBadge,
                data?.get(0)?.homeId,
                data?.get(0)?.awayId)

        swipe.isRefreshing = false

        homeName = data?.get(0)?.homeName.toString()
        awayName = data?.get(0)?.awayName.toString()
        homeScore = data?.get(0)?.homeScore.toString()
        awayScore = data?.get(0)?.awayScore.toString()

        txt_name1.text = data?.get(0)?.homeName
        txt_formation1.text = data?.get(0)?.homeFormation
        txt_goal1.text = data?.get(0)?.homeGoalDetails
        txt_goalkeeper1.text = data?.get(0)?.homeGoalKeeper
        txt_defense1.text = data?.get(0)?.homeDefense
        txt_forward1.text = data?.get(0)?.homeForward
        txt_substitutes1.text = data?.get(0)?.homeSubstitutes
        txt_midfield1.text = data?.get(0)?.homeMidfield

        txt_name2.text = data?.get(0)?.awayName
        txt_formation2.text = data?.get(0)?.awayFormation
        txt_goal2.text = data?.get(0)?.awayGoalDetails
        txt_goalkeeper2.text = data?.get(0)?.awayGoalKeeper
        txt_defense2.text = data?.get(0)?.awayDefense
        txt_forward2.text = data?.get(0)?.awayForward
        txt_substitutes2.text = data?.get(0)?.awaySubstitutes
        txt_midfield2.text = data?.get(0)?.awayMidfield

        eventDate = data?.get(0)?.dateEvent.toString()

        txt_date.text = data?.get(0)?.dateEvent
        txt_score.text = "${data?.get(0)?.homeScore} vs ${data?.get(0)?.awayScore}"
        txt_shots.text = "${data?.get(0)?.homeShots} shots ${data?.get(0)?.awayShots}"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)
        setSupportActionBar(toolbar)

        supportActionBar?.title = getString(match_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener {
            if (isFavorite) removeFromFavorite() else addToFavorite()

            isFavorite = !isFavorite
            setFavorite()
        }

        val i = intent

        homeId = i.getStringExtra(KEY.HOME_ID_KEY)
        awayId = i.getStringExtra(KEY.AWAY_ID_KEY)
        eventId = i.getStringExtra(KEY.EVENT_ID_KEY)

        favoriteState()
        val request = APIRepository()
        val gson = Gson()

        detailPresenter = DetailPresenter(this, request, gson)
        detailMatchPresenter = MatchDetailPresenter(this, request, gson)

        detailPresenter.getBadgeList(homeId, awayId)
        detailMatchPresenter.getDetailMatch(eventId)

        swipe.onRefresh {
            detailPresenter.getBadgeList(homeId, awayId)
            detailMatchPresenter.getDetailMatch(eventId)
        }
    }

    private fun favoriteState(){
        database.use {
            val result = select(FavoriteMatch.TABLE_FAVORITE)
                    .whereArgs("(TEAM_HOME_ID = {id}) and (TEAM_AWAY_ID = {id2}) and (EVENT_DATE = {id3})",
                            "id" to homeId,
                            "id2" to awayId,
                            "id3" to eventId)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(FavoriteMatch.TABLE_FAVORITE,
                        FavoriteMatch.TEAM_HOME_ID to homeId,
                        FavoriteMatch.TEAM_AWAY_ID to awayId,
                        FavoriteMatch.EVENT_DATE to eventDate,
                        FavoriteMatch.TEAM_HOME_NAME to homeName,
                        FavoriteMatch.TEAM_AWAY_NAME to awayName,
                        FavoriteMatch.TEAM_HOME_SCORE to homeScore,
                        FavoriteMatch.TEAM_AWAY_SCORE to awayScore)
            }

            toast(getString(added_fav))
        } catch (e: SQLiteConstraintException){
            toast(e.localizedMessage)
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(FavoriteMatch.TABLE_FAVORITE,
                        "(TEAM_HOME_ID = {id}) and (TEAM_AWAY_ID = {id2}) and (EVENT_DATE = {id3})",
                        "id" to homeId,
                        "id2" to awayId,
                        "id3" to eventId)
            }
            toast(getString(remove_fav))
        } catch (e: SQLiteConstraintException){
            toast(e.localizedMessage)
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            fab.setImageResource(R.drawable.ic_added_to_favorites)
        else
            fab.setImageResource(R.drawable.ic_add_to_favorites)
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
