package com.fj.footballmatchscedulefinal.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.fj.footballmatchscedulefinal.model.FavoriteMatch
import org.jetbrains.anko.db.*

class DatabaseMatch(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteMatch.db", null, 1) {
    companion object {
        private var instance: DatabaseMatch? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseMatch {
            if (instance == null) {
                instance = DatabaseMatch(ctx.applicationContext)
            }
            return instance as DatabaseMatch
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(FavoriteMatch.TABLE_FAVORITE, true,
                FavoriteMatch.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteMatch.TEAM_HOME_ID to TEXT,
                FavoriteMatch.TEAM_AWAY_ID to TEXT,
                FavoriteMatch.EVENT_DATE to TEXT,
                FavoriteMatch.TEAM_HOME_NAME to TEXT,
                FavoriteMatch.TEAM_AWAY_NAME to TEXT,
                FavoriteMatch.TEAM_HOME_SCORE to TEXT,
                FavoriteMatch.TEAM_AWAY_SCORE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavoriteMatch.TABLE_FAVORITE, true)
    }
}

val Context.databaseMatch: DatabaseMatch
    get() = DatabaseMatch.getInstance(applicationContext)