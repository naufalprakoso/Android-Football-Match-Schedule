package com.fj.footballmatchscedulefinal.model

import com.google.gson.annotations.SerializedName

data class Player(
        @SerializedName("idPlayer")
        var idPlayer: String? = null,

        @SerializedName("strPlayer")
        var strPlayer: String? = null,

        @SerializedName("strNationality")
        var strNationality: String? = null,

        @SerializedName("strTeam")
        var strTeam: String? = null,

        @SerializedName("strDescriptionEN")
        var strDescriptionEN: String? = null,

        @SerializedName("strPosition")
        var strPosition: String? = null,

        @SerializedName("strHeight")
        var strHeight: String? = null,

        @SerializedName("strWeight")
        var strWeight: String? = null,

        @SerializedName("strCutout")
        var strCutout: String? = null,

        @SerializedName("strFanart3")
        var strFanart3: String? = null
)