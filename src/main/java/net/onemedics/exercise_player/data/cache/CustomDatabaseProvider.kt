package net.onemedics.exercise_player.data.cache

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.DatabaseProvider

@UnstableApi
class CustomDatabaseProvider (context: Context) : SQLiteOpenHelper(
    context, "exo_cache.db", null, DATABASE_VERSION
), DatabaseProvider {

    companion object {
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        // 캐싱 데이터베이스 초기화
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // 데이터베이스 업그레이드 처리
    }

    override fun getWritableDatabase(): SQLiteDatabase {
        return super.getWritableDatabase()
    }

    override fun getReadableDatabase(): SQLiteDatabase {
        return super.getReadableDatabase()
    }
}