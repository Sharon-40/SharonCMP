package com.incture.kmp.sap

import android.app.Application
import com.sap.cloud.mobile.foundation.mobileservices.MobileService
import com.sap.cloud.mobile.foundation.mobileservices.SDKInitializer
import com.sap.cloud.mobile.foundation.settings.SharedDeviceService
import com.sap.cloud.mobile.foundation.theme.ThemeDownloadService

class SAPWizardApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initServices()
        koinSetup()
    }

    fun koinSetup()
    {
        initKoin {
            it.modules(
                module {
                    factory { this@BaseApplication.applicationContext }
                }
            )
        }
    }
    fun resetApplication() {

    }

    private fun initServices() {
        val services = mutableListOf<MobileService>()
        services.add(ThemeDownloadService(this))
        services.add(SharedDeviceService(OFFLINE_APP_ENCRYPTION_CONSTANT))

        SDKInitializer.start(this, * services.toTypedArray())
    }


    companion object {
        private const val OFFLINE_APP_ENCRYPTION_CONSTANT = "34dab53fc060450280faeed44a36571b"
    }
}
