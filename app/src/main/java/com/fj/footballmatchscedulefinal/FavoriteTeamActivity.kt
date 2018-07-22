package com.fj.footballmatchscedulefinal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.fj.footballmatchscedulefinal.adapter.FavoriteTeamAdapter
import com.fj.footballmatchscedulefinal.db.database
import com.fj.footballmatchscedulefinal.model.Favorite
import kotlinx.android.synthetic.main.activity_favorite_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh
import com.fj.footballmatchscedulefinal.data.KEY

class FavoriteTeamActivity : AppCompatActivity() {

    private var favorites: MutableList<Favorite> = mutableListOf()
    private lateinit var adapter: FavoriteTeamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_team)

        adapter = FavoriteTeamAdapter(this, favorites){
            startActivity<TeamDetailActivity>(KEY.TEAM_ID_KEY to "${it.teamId}")
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
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }
}
