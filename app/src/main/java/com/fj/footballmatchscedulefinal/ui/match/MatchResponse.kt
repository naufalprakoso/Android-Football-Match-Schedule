package com.fj.footballmatchscedulefinal.ui.match

import com.google.gson.annotations.SerializedName
import com.fj.footballmatchscedulefinal.model.Match
import com.fj.footballmatchscedulefinal.model.TeamMatch

data class MatchResponse(
        @field:SerializedName("events")
        val events: List<Match>? = null,

        @field:SerializedName("teams")
        val teamId: List<TeamMatch>? = null
)