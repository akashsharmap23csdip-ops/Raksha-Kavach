package com.raksha.kavach.utils

import com.raksha.kavach.R

enum class RiskLevel(val label: String, val badgeRes: Int) {
    SAFE("Safe", R.drawable.bg_risk_safe),
    MEDIUM("Medium Risk", R.drawable.bg_risk_medium),
    HIGH("High Risk", R.drawable.bg_risk_high)
}
