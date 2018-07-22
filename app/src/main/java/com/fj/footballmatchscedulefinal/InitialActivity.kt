package com.fj.footballmatchscedulefinal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_initial.*
import org.jetbrains.anko.startActivity

class InitialActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(p0: View?) {
        when(p0){
            btn_team -> startActivity<TeamActivity>()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)

        btn_favorite.setOnClickListener(this)
        btn_match.setOnClickListener(this)
        btn_team.setOnClickListener(this)
    }
}
