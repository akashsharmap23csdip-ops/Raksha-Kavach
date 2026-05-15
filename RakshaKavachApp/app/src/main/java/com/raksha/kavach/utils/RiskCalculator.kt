package com.raksha.kavach.utils

object RiskCalculator {
    fun calculateRisk(missingCount: Int): RiskLevel {
        return when {
            missingCount == 0 -> RiskLevel.SAFE
            missingCount <= 2 -> RiskLevel.MEDIUM
            else -> RiskLevel.HIGH
        }
    }

    fun scoreDelta(level: RiskLevel): Int {
        return when (level) {
            RiskLevel.SAFE -> 5
            RiskLevel.MEDIUM -> 0
            RiskLevel.HIGH -> -10
        }
    }
}
