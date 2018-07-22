package com.fj.footballmatchscedulefinal.api

import com.fj.footballmatchscedulefinal.BuildConfig

object TheSportDBApi{
    fun getTeams(team: String?): String{
        return BuildConfig.BASE_URL +
                "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/search_all_teams.php?l=" + team
    }

    fun getTeamDetail(team: String?): String{
        return BuildConfig.BASE_URL +
                "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupteam.php?id=" + team
    }

    fun getPlayer(player: String?): String{
        println("Info : " + BuildConfig.BASE_URL +
                "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookup_all_players.php?id=" + player)
        return BuildConfig.BASE_URL +
                "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookup_all_players.php?id=" + player
    }
}