package com.bill.renote.data

class InAppFeatureFlagProvider {
    private val inAppFeatureFlags = InAppFeatureFlags(
        isDebugBuild = BuildConfig.DEBUG
    )

    fun provide(): InAppFeatureFlags = inAppFeatureFlags
}