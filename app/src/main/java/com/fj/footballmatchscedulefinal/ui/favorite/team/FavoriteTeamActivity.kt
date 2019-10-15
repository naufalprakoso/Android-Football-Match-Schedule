package com.fj.footballmatchscedulefinal.ui.favorite.team

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.fj.footballmatchscedulefinal.R
import kotlinx.android.synthetic.main.activity_favorite_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh
import com.fj.footballmatchscedulefinal.data.Const
import com.fj.footballmatchscedulefinal.db.database
import com.fj.footballmatchscedulefinal.model.FavoriteTeam
import com.fj.footballmatchscedulefinal.R.string.*
import com.fj.footballmatchscedulefinal.ui.team.detail.TeamDetailActivity

class FavoriteTeamActivity : AppCompatActivity() {

    private var favorites: MutableList<FavoriteTeam> = mutableListOf()
    private lateinit var adapter: FavoriteTeamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_team)

        supportActionBar?.title = getString(fav_team)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = FavoriteTeamAdapter(this, favorites) {
            startActivity<TeamDetailActivity>(Const.TEAM_ID_KEY to "${it.teamId}")
        }

        rv_team.layoutManager = GridLayoutManager(this, 2)
        rv_team.adapter = adapter
        showFavorite()
        swipe.onRefresh {
            favorites.clear()
            showFavorite()
        }
    }

    private fun showFavorite(){
        database.use {
            swipe.isRefreshing = false
            val result = select(FavoriteTeam.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
