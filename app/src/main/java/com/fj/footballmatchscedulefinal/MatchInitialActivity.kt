package com.fj.footballmatchscedulefinal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_match_initial.*
import org.jetbrains.anko.startActivity

class MatchInitialActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(p0: View?) {
        when(p0){
            btn_past -> startActivity<MatchActivity>()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_initial)

        supportActionBar?.title = "Match"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btn_past.setOnClickListener(this)
        btn_next.setOnClickListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
