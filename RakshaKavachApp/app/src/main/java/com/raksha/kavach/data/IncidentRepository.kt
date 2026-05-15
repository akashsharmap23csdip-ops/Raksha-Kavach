package com.raksha.kavach.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

class IncidentRepository(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun saveIncident(incident: Incident): Boolean {
        val list = JSONArray(prefs.getString(KEY_LIST, "[]"))
        val obj = JSONObject()
        obj.put("title", incident.title)
        obj.put("description", incident.description)
        obj.put("date", incident.date)
        obj.put("imageUri", incident.imageUri ?: "")
        list.put(obj)
        prefs.edit().putString(KEY_LIST, list.toString()).apply()
        return true
    }

    fun getIncidents(): List<Incident> {
        val list = JSONArray(prefs.getString(KEY_LIST, "[]"))
        val results = mutableListOf<Incident>()
        for (i in 0 until list.length()) {
            val obj = list.getJSONObject(i)
            results.add(
                Incident(
                    title = obj.optString("title"),
                    description = obj.optString("description"),
                    date = obj.optString("date"),
                    imageUri = obj.optString("imageUri").ifEmpty { null }
                )
            )
        }
        return results
    }

    companion object {
        private const val PREFS = "raksha_incidents"
        private const val KEY_LIST = "incidents"
    }
}
