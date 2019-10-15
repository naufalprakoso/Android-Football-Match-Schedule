package com.fj.footballmatchscedulefinal.ui.match

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.fj.footballmatchscedulefinal.R
import kotlinx.android.synthetic.main.activity_match_initial.*
import org.jetbrains.anko.startActivity
import com.fj.footballmatchscedulefinal.R.string.*
import com.fj.footballmatchscedulefinal.ui.match.all.past.MatchActivity
import com.fj.footballmatchscedulefinal.ui.match.all.next.NextMatchActivity

class MatchInitialActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(p0: View?) {
        when(p0){
            btn_past -> startActivity<MatchActivity>()
            btn_next -> startActivity<NextMatchActivity>()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_initial)

        supportActionBar?.title = getString(match)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btn_past.setOnClickListener(this)
        btn_next.setOnClickListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
