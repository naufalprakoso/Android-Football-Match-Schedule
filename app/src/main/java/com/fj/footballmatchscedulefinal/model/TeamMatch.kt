package com.fj.footballmatchscedulefinal.model

import com.google.gson.annotations.SerializedName

data class TeamMatch(
        @SerializedName("strTeamBadge")
        var teamBadge: String? = null
)