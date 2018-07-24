package com.fj.footballmatchscedulefinal.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.fj.footballmatchscedulefinal.model.FavoriteMatch
import com.fj.footballmatchscedulefinal.model.FavoriteTeam
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "Favorite.db", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance!!
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

        db.createTable(FavoriteTeam.TABLE_FAVORITE, true,
                FavoriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteTeam.TEAM_ID to TEXT + UNIQUE,
                FavoriteTeam.TEAM_NAME to TEXT,
                FavoriteTeam.TEAM_BADGE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavoriteMatch.TABLE_FAVORITE, true)
        db.dropTable(FavoriteTeam.TABLE_FAVORITE, true)

    }
}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)