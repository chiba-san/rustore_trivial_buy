package com.sample.android.trivialbuy

import android.app.Application
import ru.rustore.sdk.billingclient.RuStoreBillingClient

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        RuStoreBillingClient.init(
            appContext = applicationContext,
            applicationId = BuildConfig.APPLICATION_ID,
            consoleApplicationId = "508863",
            deeplinkPrefix = "yourappscheme://iamback"
        )
    }
}
