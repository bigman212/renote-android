package com.bill.renote.data

import com.bill.renote.BuildConfig

class InAppFeatureFlagProvider {
    private val inAppFeatureFlags = InAppFeatureFlags(
        isDebugBuild = BuildConfig.DEBUG
    )

    fun provide(): InAppFeatureFlags = inAppFeatureFlags
}