package com.raksha.kavach.data

import android.content.Context

class ScoreRepository(private val context: Context) {
    private val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun getScore(): Int = prefs.getInt(KEY_SCORE, 100)

    fun updateScore(delta: Int): Int {
        val newScore = (getScore() + delta).coerceIn(0, 999)
        prefs.edit().putInt(KEY_SCORE, newScore).apply()
        return newScore
    }

    companion object {
        private const val PREFS = "raksha_prefs"
        private const val KEY_SCORE = "safety_score"
    }
}
