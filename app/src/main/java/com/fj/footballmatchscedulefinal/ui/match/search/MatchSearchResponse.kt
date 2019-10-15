package com.fj.footballmatchscedulefinal.ui.match.search

import com.google.gson.annotations.SerializedName
import com.fj.footballmatchscedulefinal.model.Match

data class MatchSearchResponse(
        @field:SerializedName("event")
        val event: List<Match>? = null
)